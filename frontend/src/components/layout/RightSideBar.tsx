const recommendedSeries = [
  { count: '04 Articles', title: 'Tailwind CSS 마스터 클래스' },
  { count: '07 Articles', title: '모던 자바스크립트 깊게 파기' },
];

const popularTags = ['#React', '#Frontend', '#TypeScript', '#Next.js', '#UI/UX', '#Node.js'];

export default function RightSideBar() {
  return (
    <div className="hidden xl:flex flex-col gap-6 w-80 sticky top-24 h-[calc(100vh-120px)]">
      {/* 추천 시리즈 */}
      <aside className="flex flex-col p-8 gap-6 rounded-l-[3rem] glass-panel overflow-y-auto">
        <div className="flex items-center justify-between">
          <h3 className="font-black text-lg text-[#1c1c1a]">추천 시리즈</h3>
          <span className="material-symbols-outlined text-[#5c6e78]">auto_awesome</span>
        </div>
        <div className="space-y-3">
          {recommendedSeries.map((series) => (
            <a key={series.title} href="#" className="block group">
              <div className="glass-card p-4 rounded-2xl group-hover:border-[#5c6e78]/25 transition-all">
                <p className="text-xs font-bold text-[#5c6e78] mb-1 tracking-widest uppercase">
                  {series.count}
                </p>
                <h4 className="font-bold text-[#1c1c1a] leading-tight group-hover:text-[#5c6e78] transition-colors">
                  {series.title}
                </h4>
              </div>
            </a>
          ))}
        </div>
      </aside>

      {/* 인기 태그 */}
      <aside className="flex flex-col p-8 gap-6 rounded-l-[3rem] glass-panel overflow-y-auto">
        <div className="flex items-center justify-between">
          <h3 className="font-black text-lg text-[#1c1c1a]">인기 태그</h3>
          <span className="material-symbols-outlined text-[#797976]">sell</span>
        </div>
        <div className="flex flex-wrap gap-2">
          {popularTags.map((tag) => (
            <a
              key={tag}
              href="#"
              className="glass-card px-4 py-2 rounded-full text-sm font-semibold text-[#494946] hover:bg-[#5c6e78] hover:text-[#f4f6f7] hover:border-transparent hover:shadow-[0_4px_14px_rgba(92,110,120,0.28)] transition-all"
            >
              {tag}
            </a>
          ))}
        </div>
      </aside>
    </div>
  );
}
