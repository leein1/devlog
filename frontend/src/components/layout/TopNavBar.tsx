import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

export default function TopNavBar() {
  const [dark, setDark] = useState(() => localStorage.getItem('theme') === 'dark');

  useEffect(() => {
    document.documentElement.classList.toggle('dark', dark);
    localStorage.setItem('theme', dark ? 'dark' : 'light');
  }, [dark]);

  return (
    <nav className="fixed top-0 w-full z-50 glass-panel flex justify-between items-center px-8 py-4">
      <div className="flex items-center gap-12">
        <Link to="/" className="text-2xl font-black tracking-tighter text-on-surface">
          devlog
        </Link>
        <div className="hidden md:flex gap-2 items-center">
          <Link
            to="/"
            className="text-primary font-bold px-4 py-1.5 rounded-full bg-primary/8 border border-primary/15"
          >
            게시글
          </Link>
          <a
            className="text-on-surface-variant font-medium hover:bg-on-surface/5 transition-all duration-200 px-4 py-1.5 rounded-full"
            href="#"
          >
            시리즈
          </a>
          <a
            className="text-on-surface-variant font-medium hover:bg-on-surface/5 transition-all duration-200 px-4 py-1.5 rounded-full"
            href="#"
          >
            카테고리
          </a>
        </div>
      </div>
      <div className="flex items-center gap-4">
        <div className="rainbow-border group hidden md:block">
          <Link
            to="/portfolio"
            className="relative z-[1] inline-flex items-center gap-1.5 px-5 py-2 rounded-full font-bold text-sm text-on-surface whitespace-nowrap bg-surface-container-low border border-outline-variant"
          >
            Portfolio
          </Link>
          <span className="pointer-events-none absolute left-1/2 top-full mt-2 -translate-x-1/2 whitespace-nowrap rounded-full border border-outline-variant bg-surface-container-low px-3 py-1 text-xs text-on-surface-variant opacity-0 shadow-sm transition-opacity duration-300 group-hover:opacity-100">
            포트폴리오 글을 모아볼 수 있습니다
          </span>
        </div>
        <Link
          to="/write"
          className="hidden md:inline-flex items-center gap-1.5 px-5 py-2 rounded-full font-bold text-sm text-primary border border-primary/25 hover:bg-primary/8 transition-all"
        >
          <span className="material-symbols-outlined text-[18px]">edit</span>
          글쓰기
        </Link>
        <button
          onClick={() => setDark(!dark)}
          className="p-2 rounded-full text-on-surface-variant hover:bg-on-surface/5 transition-all"
          aria-label="다크모드 토글"
        >
          <span className="material-symbols-outlined text-[20px]">
            {dark ? 'light_mode' : 'dark_mode'}
          </span>
        </button>
        <button className="bg-primary text-on-primary px-6 py-2 rounded-full font-bold transition-all hover:bg-primary-container active:scale-95 shadow-[0_4px_14px_rgba(92,110,120,0.28)]">
          Login
        </button>
      </div>
    </nav>
  );
}
