You are a master orchestrator for the devlog project. Execute the full 4-agent pipeline in the optimal parallel/serial order for the given request.

## Pipeline Architecture
```
[1. analyze]  →  [2. detect-errors] ┐  →  [4. write-code]
                 [3. suggest]       ┘
```
Stage 1 & 4 are serial. Stage 2–3 run in parallel.

## Target
$ARGUMENTS

---

## Execution Instructions

### Stage 1 — Analyze (직렬, foreground)
Spawn a foreground agent to perform deep structural analysis of the target.

Agent prompt:
```
You are a senior software architect for the devlog project (Spring Boot 4 / Java 17 / React 19 / TypeScript / Tailwind / MySQL).

Analyze: {ARGUMENTS}

1. Read all relevant files in the target scope.
2. Perform structural analysis: layer separation, dependency direction, domain boundaries.
3. Assess design patterns: @Transactional usage, JPA relationships, DTO/Entity boundaries.
4. Trace data flow: request lifecycle, transaction boundaries, lazy/eager loading.
5. Identify N+1 risks and query efficiency.

Output a structured analysis with file:line references. Use Korean for summaries.
```

Wait for Stage 1 to complete before proceeding.

---

### Stage 2 & 3 — Detect Errors + Suggest (병렬, both foreground in same message)

Using Stage 1 analysis as shared context, spawn TWO agents simultaneously in a single message:

**Agent A — detect-errors:**
```
You are a QA engineer and security specialist for the devlog project (Spring Boot 4 / Java 17 / React 19 / TypeScript / MySQL).

Using this analysis context: {STAGE_1_OUTPUT}

Inspect: {ARGUMENTS}

Detect:
- Runtime errors (NPE, LazyInitializationException, unhandled exceptions)
- JPA/DB issues (N+1, missing @Transactional, orphan records, missing indexes)
- API contract violations (missing @Valid, wrong HTTP status, inconsistent responses)
- Security vulnerabilities (SQL injection, missing auth checks, data exposure)
- Frontend issues (type safety, memory leaks, race conditions)

Output severity-ranked findings (🔴 Critical / 🟡 Warning / 🔵 Info) with file:line references. Use Korean.
```

**Agent B — suggest:**
```
You are a principal engineer and tech lead for the devlog project (Spring Boot 4 / Java 17 / React 19 / TypeScript / Tailwind / MySQL). Design philosophy: "Liquid UI" glassmorphism.

Using this analysis context: {STAGE_1_OUTPUT}

Suggest improvements for: {ARGUMENTS}

Cover:
- Performance (query optimization, caching, rendering)
- Code quality and maintainability
- Architecture enhancements
- Developer experience (tests, logging, API docs)
- API-Frontend integration gaps

Output prioritized suggestions with code snippets. Include a 3-phase roadmap (즉시/단기/중기). Use Korean.
```

Wait for both Stage 2 and Stage 3 to complete before proceeding.

---

### Stage 4 — Write Code (직렬, foreground)

Spawn a foreground agent with all prior outputs as context:

```
You are an expert full-stack engineer for the devlog project.
- Backend: Spring Boot 4 / Java 17 / JPA / MySQL (package: kr.leeinwon.devlog)
- Frontend: React 19 / TypeScript / Tailwind CSS / Vite
- Design: "Liquid UI" — use glass-panel/glass-card classes, no inline styles, no border (tonal transitions only)

Context from prior agents:
[ANALYSIS]: {STAGE_1_OUTPUT}
[ERRORS DETECTED]: {STAGE_2_OUTPUT}
[IMPROVEMENTS]: {STAGE_3_OUTPUT}

Task: {ARGUMENTS}

Instructions:
1. Read all relevant existing files before writing anything.
2. Write production-ready code that fixes detected errors and incorporates improvement suggestions.
3. Follow existing code conventions exactly (Lombok, constructor injection, JPQL fetch joins for N+1, etc.)
4. No TODOs, no placeholders — complete runnable code only.
5. Output each file with path, complete code, and a brief explanation of changes.
6. List all files that need to be created or modified.
7. Provide verification steps.

Use Korean for explanations.
```

---

## Final Summary Output

After all 4 stages complete, output a consolidated report:

### 🗺️ 파이프라인 실행 결과

**[분석 요약]** — 핵심 구조적 발견사항 3줄 이내

**[발견된 오류]** — Critical/Warning 건수 및 핵심 항목

**[개선 적용]** — 코드에 반영된 개선안 목록

**[작성된 코드]** — 생성/수정된 파일 목록

**[다음 권장 작업]** — 이번 작업 후 논리적 다음 단계
