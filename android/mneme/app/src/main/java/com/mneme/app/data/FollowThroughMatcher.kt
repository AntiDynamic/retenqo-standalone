package com.mneme.app.data

/** Conservative lexical matcher for completion evidence. It proposes; it never changes task state. */
object FollowThroughMatcher {
    private val negatedCompletion = Regex(
        """(?i)\b(?:not|never|haven't|hasn't|have not|has not|didn't|did not|won't|will not|can't|cannot)\b[\s\w'-]{0,36}\b(?:sent|shared|delivered|submitted|uploaded|finished|completed|done|received|got it|all set|sorted)\b""",
    )
    private val futureCompletion = Regex(
        """(?i)\b(?:(?:i|we|they|you)\s+(?:will|shall|can|might|may|plan to|need to|hope to|going to|won't|can't)|i['’]ll|we['’]ll|they['’]ll|you['’]ll)\s+(?:[\w'-]+\s+){0,4}(?:send you|share you|sent|shared|delivered|submitted|uploaded|finished|completed|done|received|got it|all set|sorted)\b""",
    )
    private val completionLanguage = Regex(
        """(?i)\b(sent|send\s+you|shared|share\s+you|delivered|submitted|uploaded|finished|completed|done|received|got\s+it|all\s+set|sorted|thanks\s+for\s+(sending|sharing))\b""",
    )

    private val nonAffirmativeContext = Regex("""(?i)^\s*(?:did|have|has|was|were|are|is|if|unless|whether)\b|[?？]$|[‘’“”\"].*[‘’“”\"]""")

    /** Reject contexts where a completion verb does not assert that the event happened. */
    fun isAffirmativeCompletionEvidence(evidence: String): Boolean =
        !negatedCompletion.containsMatchIn(evidence) &&
            !futureCompletion.containsMatchIn(evidence) &&
            !nonAffirmativeContext.containsMatchIn(evidence)

    data class Match(val commitment: NoteEntity, val confidence: Float)

    fun find(evidence: String, commitments: List<NoteEntity>): Match? {
        if (!isAffirmativeCompletionEvidence(evidence)) return null
        if (!completionLanguage.containsMatchIn(evidence)) return null
        val evidenceTokens = MemoryLexicalSearch.tokens(evidence)
        if (evidenceTokens.isEmpty()) return null

        return commitments.asSequence()
            .filter { it.isActive() && it.type() in setOf(CardType.PROMISE, CardType.DUE, CardType.ASK_FOR_YOU, CardType.WAITING) }
            .mapNotNull { note ->
                val commitmentTokens = MemoryLexicalSearch.tokens("${note.title} ${note.body}")
                    .filterNot { it in GENERIC_COMMITMENT_WORDS }
                    .toSet()
                val shared = evidenceTokens.intersect(commitmentTokens)
                // A shared concrete subject is required. Generic completion language alone is weak.
                if (shared.isEmpty()) return@mapNotNull null
                val confidence = (0.58f + 0.12f * shared.size.coerceAtMost(3)).coerceAtMost(0.94f)
                Match(note, confidence)
            }
            .maxByOrNull { it.confidence }
    }

    private val GENERIC_COMMITMENT_WORDS = setOf(
        "send", "sent", "share", "shared", "please", "need", "could", "would", "will", "can",
        "you", "your", "me", "them", "they", "from", "with", "about", "when", "what",
    )
}
