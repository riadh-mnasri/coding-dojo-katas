# Copyright (c) 2026 Riadh MNASRI. Licensed under the MIT License.
# Régénère le tableau des katas des README racine à partir de scripts/katas.tsv.
import os, re, sys
root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
rows = [l.rstrip("\n").split("\t") for l in open(os.path.join(os.path.dirname(__file__), "katas.tsv")) if l.strip()]
def done(slug): return os.path.exists(f"{root}/katas/{slug}/README.md")
n = sum(done(r[0]) for r in rows)
def table(lang):
    if lang == "fr":
        head = f"**Avancement : {n} / {len(rows)} katas.**\n\n| Kata | Ce qu'il fait travailler | Statut | Énoncé |\n|---|---|---|---|\n"
        ok, todo, doc = "✅ fait", "⏳ à faire", "README.md"
    else:
        head = f"**Progress: {n} / {len(rows)} katas.**\n\n| Kata | What it practises | Status | Kata page |\n|---|---|---|---|\n"
        ok, todo, doc = "✅ done", "⏳ to do", "README.en.md"
    out = []
    for slug, name, up, fr, en in rows:
        label = f"[{name}](katas/{slug}/{doc})" if done(slug) else name
        out.append(f"| {label} | {fr if lang=='fr' else en} | {ok if done(slug) else todo} | [codingdojo.org](https://codingdojo.org/kata/{up}/) |")
    return head + "\n".join(out) + "\n"
for f, lang in (("README.md", "fr"), ("README.en.md", "en")):
    p = f"{root}/{f}"; s = open(p).read()
    s = re.sub(r"<!-- katas:start -->.*<!-- katas:end -->", f"<!-- katas:start -->\n{table(lang)}<!-- katas:end -->", s, flags=re.S)
    open(p, "w").write(s)
print(f"{n}/{len(rows)}")
