import { readFileSync } from 'node:fs';
import { resolve } from 'node:path';
import { describe, expect, it } from 'vitest';

describe('hosting security headers', () => {
  it('keeps the Vercel policy equivalent to Cloudflare Pages', () => {
    const root = process.cwd();
    const cloudflare = readFileSync(resolve(root, 'public/_headers'), 'utf8');
    const vercel = JSON.parse(
      readFileSync(resolve(root, 'vercel.json'), 'utf8')
    ) as {
      headers: Array<{
        source: string;
        headers: Array<{ key: string; value: string }>;
      }>;
    };
    const cloudflareHeaders = Object.fromEntries(
      cloudflare
        .split('\n')
        .map((line) => /^\s+([^:#]+):\s*(.+?)\s*$/.exec(line))
        .filter((match): match is RegExpExecArray => match !== null)
        .map(([, key, value]) => [key, value])
    );
    const vercelRule = vercel.headers.find((rule) => rule.source === '/(.*)');
    const vercelHeaders = Object.fromEntries(
      (vercelRule?.headers ?? []).map(({ key, value }) => [key, value])
    );

    expect(vercelRule).toBeDefined();
    expect(vercelHeaders).toEqual(cloudflareHeaders);
  });
});
