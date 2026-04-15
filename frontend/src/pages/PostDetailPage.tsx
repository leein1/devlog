const tags = ['#React', '#Frontend', '#Animation'];

const tableOfContents = [
  { id: 'intro', level: 1, label: '들어가며' },
  { id: 'concept', level: 1, label: 'Liquid UI 개념' },
  { id: 'motion', level: 2, label: 'Framer Motion 기초' },
  { id: 'layout', level: 2, label: '레이아웃 애니메이션' },
  { id: 'shared', level: 2, label: 'Shared Layout' },
  { id: 'best', level: 1, label: '실전 Best Practices' },
  { id: 'conclusion', level: 1, label: '마치며' },
];

interface PostDetailPageProps {
  onBack: () => void;
}

export default function PostDetailPage({ onBack }: PostDetailPageProps) {
  return (
    <main className="flex-1 min-w-0 pb-20 px-12 flex gap-8">
      {/* 본문 */}
      <article className="flex-1 min-w-0">
        {/* 뒤로 가기 */}
        <button
          onClick={onBack}
          className="mb-8 inline-flex items-center gap-1.5 text-sm font-bold text-[#5c6e78] hover:text-[#4a5a63] transition-colors"
        >
          <span className="material-symbols-outlined text-[18px]">arrow_back</span>
          목록으로
        </button>

        {/* 헤더 */}
        <header className="mb-10 space-y-5">
          <div className="flex items-center gap-3">
            <span className="text-xs font-bold text-[#5c6e78] tracking-widest uppercase">
              Frontend
            </span>
            <span className="w-1 h-1 rounded-full bg-[#c8c8c5]" />
            <time className="text-xs font-medium text-[#797976]">Oct 24, 2023</time>
            <span className="w-1 h-1 rounded-full bg-[#c8c8c5]" />
            <span className="text-xs font-medium text-[#797976]">12 min read</span>
          </div>

          <h1 className="text-5xl font-black text-[#1c1c1a] tracking-tighter leading-[1.1]">
            Implementing Framer Motion for Editorial Flow
          </h1>

          <p className="text-[#494946] text-lg leading-relaxed font-medium">
            A deep dive into creating natural, liquid transitions between layout states without
            breaking the user's mental model.
          </p>

          {/* 태그 */}
          <div className="flex flex-wrap gap-2 pt-1">
            {tags.map((tag) => (
              <span
                key={tag}
                className="glass-card px-4 py-1.5 rounded-full text-sm font-semibold text-[#494946]"
              >
                {tag}
              </span>
            ))}
          </div>
        </header>

        {/* 구분선 */}
        <div className="h-px bg-gradient-to-r from-transparent via-[#c8c8c5]/60 to-transparent mb-10" />

        {/* 본문 콘텐츠 */}
        <div className="prose-content space-y-8 text-[#1c1c1a]">
          <section className="scroll-mt-28" id="intro">
            <h2 className="text-2xl font-extrabold tracking-tight mb-4">들어가며</h2>
            <p className="text-[#494946] leading-relaxed font-medium">
              현대 웹 인터페이스에서 애니메이션은 단순한 장식이 아닙니다. 사용자가 화면 상의
              요소들이 어디서 왔고 어디로 가는지를 이해할 수 있도록 돕는 핵심적인 커뮤니케이션
              수단입니다. Framer Motion은 React 생태계에서 이 역할을 가장 우아하게 수행하는
              라이브러리입니다.
            </p>
          </section>

          <section className="scroll-mt-28" id="concept">
            <h2 className="text-2xl font-extrabold tracking-tight mb-4">Liquid UI 개념</h2>
            <p className="text-[#494946] leading-relaxed font-medium">
              Liquid UI란 UI 요소들이 고체처럼 딱딱하게 나타나고 사라지는 것이 아니라, 액체처럼
              자연스럽게 흘러 이동하는 인터페이스 패러다임을 의미합니다. 이는 사용자의 시선을
              자연스럽게 유도하고 맥락의 연속성을 제공합니다.
            </p>
            <div className="glass-card rounded-2xl p-6 mt-4 border-l-4 border-[#5c6e78]/40">
              <p className="text-[#494946] font-medium italic">
                "The best animations are the ones users don't notice — they just feel right."
              </p>
            </div>
          </section>

          <section className="scroll-mt-28" id="motion">
            <h2 className="text-2xl font-extrabold tracking-tight mb-3">Framer Motion 기초</h2>
            <p className="text-[#494946] leading-relaxed font-medium mb-4">
              Framer Motion의 핵심은 <code className="bg-[#eceae7] px-2 py-0.5 rounded text-sm font-mono text-[#5c6e78]">motion</code> 컴포넌트입니다.
              일반 HTML 요소 앞에 <code className="bg-[#eceae7] px-2 py-0.5 rounded text-sm font-mono text-[#5c6e78]">motion.</code>을 붙이는 것만으로
              애니메이션이 가능한 요소로 변환됩니다.
            </p>
            <div className="glass-panel rounded-2xl p-6 font-mono text-sm text-[#494946] overflow-x-auto">
              <pre>{`import { motion } from 'framer-motion';

function Card() {
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -20 }}
      transition={{ duration: 0.4, ease: 'easeOut' }}
    >
      Hello, Liquid UI!
    </motion.div>
  );
}`}</pre>
            </div>
          </section>

          <section className="scroll-mt-28" id="layout">
            <h2 className="text-2xl font-extrabold tracking-tight mb-4">레이아웃 애니메이션</h2>
            <p className="text-[#494946] leading-relaxed font-medium">
              <code className="bg-[#eceae7] px-2 py-0.5 rounded text-sm font-mono text-[#5c6e78]">layout</code> prop은
              Framer Motion의 가장 강력한 기능 중 하나입니다. 요소의 크기나 위치가 변경될 때
              자동으로 부드러운 전환을 적용해 줍니다. CSS transition으로는 구현이 어려운 복잡한
              레이아웃 변화도 한 줄의 prop으로 해결됩니다.
            </p>
          </section>

          <section className="scroll-mt-28" id="shared">
            <h2 className="text-2xl font-extrabold tracking-tight mb-4">Shared Layout</h2>
            <p className="text-[#494946] leading-relaxed font-medium">
              <code className="bg-[#eceae7] px-2 py-0.5 rounded text-sm font-mono text-[#5c6e78]">layoutId</code>를
              활용하면 완전히 다른 컴포넌트 트리에 있는 두 요소 사이의 전환도 마치 하나의 요소가
              이동하는 것처럼 구현할 수 있습니다. 리스트 아이템이 확장되어 상세 카드가 되는 UI가
              대표적인 예시입니다.
            </p>
          </section>

          <section className="scroll-mt-28" id="best">
            <h2 className="text-2xl font-extrabold tracking-tight mb-4">실전 Best Practices</h2>
            <ul className="space-y-3">
              {[
                '애니메이션 지속 시간은 200–400ms 사이를 유지하세요.',
                'ease-out 커브는 자연스러운 감속감을 줍니다.',
                '모션에 민감한 사용자를 위해 prefers-reduced-motion을 반드시 지원하세요.',
                '레이아웃 애니메이션 남용은 오히려 혼란을 야기할 수 있습니다.',
              ].map((tip) => (
                <li key={tip} className="flex items-start gap-3">
                  <span
                    className="material-symbols-outlined text-[#5c6e78] text-[18px] mt-0.5 shrink-0"
                    style={{ fontVariationSettings: "'FILL' 1" }}
                  >
                    check_circle
                  </span>
                  <span className="text-[#494946] font-medium leading-relaxed">{tip}</span>
                </li>
              ))}
            </ul>
          </section>

          <section className="scroll-mt-28" id="conclusion">
            <h2 className="text-2xl font-extrabold tracking-tight mb-4">마치며</h2>
            <p className="text-[#494946] leading-relaxed font-medium">
              Framer Motion은 단순히 애니메이션 라이브러리가 아닙니다. 사용자 경험을 한 단계
              끌어올리는 커뮤니케이션 도구입니다. 오늘 살펴본 개념들을 실제 프로젝트에 적용해
              보시고, 여러분만의 Liquid UI를 만들어 보세요.
            </p>
          </section>
        </div>

        {/* 구분선 */}
        <div className="h-px bg-gradient-to-r from-transparent via-[#c8c8c5]/60 to-transparent my-12" />

        {/* 이전 / 다음 글 */}
        <nav className="grid grid-cols-2 gap-4">
          <div className="glass-card rounded-2xl p-5 group cursor-pointer hover:border-[#5c6e78]/25 transition-all">
            <p className="text-xs font-bold text-[#797976] tracking-widest uppercase mb-2 flex items-center gap-1">
              <span className="material-symbols-outlined text-[14px]">arrow_back</span>
              이전 글
            </p>
            <p className="font-extrabold text-[#1c1c1a] leading-tight text-sm group-hover:text-[#5c6e78] transition-colors">
              CSS Grid로 만드는 Editorial Layout
            </p>
          </div>
          <div className="glass-card rounded-2xl p-5 group cursor-pointer hover:border-[#5c6e78]/25 transition-all text-right">
            <p className="text-xs font-bold text-[#797976] tracking-widest uppercase mb-2 flex items-center gap-1 justify-end">
              다음 글
              <span className="material-symbols-outlined text-[14px]">arrow_forward</span>
            </p>
            <p className="font-extrabold text-[#1c1c1a] leading-tight text-sm group-hover:text-[#5c6e78] transition-colors">
              Mastering Server Components in Next.js
            </p>
          </div>
        </nav>
      </article>

      {/* 목차 사이드바 (데스크톱) */}
      <aside className="hidden xl:flex flex-col gap-4 w-56 sticky top-28 self-start">
        <div className="glass-panel rounded-2xl p-5">
          <h3 className="text-xs font-black tracking-widest uppercase text-[#5c6e78] mb-4">
            목차
          </h3>
          <nav className="space-y-1">
            {tableOfContents.map((item) => (
              <button
                key={item.id}
                onClick={() => {
                  document.getElementById(item.id)?.scrollIntoView({ behavior: 'smooth', block: 'start' });
                }}
                className={`block w-full text-left text-sm font-medium text-[#797976] hover:text-[#5c6e78] transition-colors leading-snug py-0.5 ${
                  item.level === 2 ? 'pl-3 text-xs' : ''
                }`}
              >
                {item.label}
              </button>
            ))}
          </nav>
        </div>
      </aside>
    </main>
  );
}
