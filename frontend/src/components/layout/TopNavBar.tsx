// interface TopNavBarProps {
//   onWrite?: () => void;
// }

// export default function TopNavBar({ onWrite }: TopNavBarProps) {
import {Link} from "react-router-dom";

export default function TopNavBar( ) {
  return (
    <nav className="fixed top-0 w-full z-50 glass-panel flex justify-between items-center px-8 py-4">
      <div className="flex items-center gap-12">
        <Link to="/" className="text-2xl font-black tracking-tightertext-[#1c1c1a]">
          devlog
        </Link>
        <div className="hidden md:flex gap-2 items-center">
          <Link to="/" className="text-[#5c6e78] font-bold px-4 py-1.5 rounded-full bg-[#5c6e78]/8 border border-[#5c6e78]/15">
            게시글
          </Link>
          <a
            className="text-[#494946] font-medium hover:bg-[#1c1c1a]/5 transition-all duration-200 px-4 py-1.5 rounded-full"
            href="#"
          >
            시리즈
          </a>
          <a
            className="text-[#494946] font-medium hover:bg-[#1c1c1a]/5 transition-all duration-200 px-4 py-1.5 rounded-full"
            href="#"
          >
            카테고리
          </a>
        </div>
      </div>
      <div className="flex items-center gap-4">
        <div className="rainbow-border group hidden md:block">
          <Link to="/portfolio" className="relative z-[1] inline-flex items-center gap-1.5 px-5 py-2 rounded-full font-bold text-sm text-[#1c1c1a] whitespace-nowrap bg-[#f8f8f5] border border-[#ddddd9]">
            Portfolio
          </Link>
          <span className="pointer-events-none absolute left-1/2 top-full mt-2 -translate-x-1/2 whitespace-nowrap rounded-full border border-[#ddddd9] bg-[#f8f8f5] px-3 py-1 text-xs text-[#494946] opacity-0 shadow-sm transition-opacity duration-300 group-hover:opacity-100">
            포트폴리오 글을 모아볼 수 있습니다
          </span>
        </div>
        <Link
            to="/write"
            className="hidden md:inline-flex items-center gap-1.5 px-5 py-2 rounded-full
  font-bold text-sm text-[#5c6e78] border border-[#5c6e78]/25 hover:bg-[#5c6e78]/8
  transition-all">
          <span className="material-symbols-outlined text-[18px]">edit</span>
          글쓰기
        </Link>
        <button className="bg-[#5c6e78] text-[#f4f6f7] px-6 py-2 rounded-full font-bold transition-all hover:bg-[#4a5a63] active:scale-95 shadow-[0_4px_14px_rgba(92,110,120,0.28)]">
          Login
        </button>
      </div>
    </nav>
  );
}
