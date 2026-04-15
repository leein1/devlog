import { useState } from 'react';
import TopNavBar from './components/layout/TopNavBar';
import LeftSideBar from './components/layout/LeftSideBar';
import RightSideBar from './components/layout/RightSideBar';
import MobileBottomNav from './components/layout/MobileBottomNav';
import HomePage from './pages/HomePage';
import PostDetailPage from './pages/PostDetailPage';

type Page = 'home' | 'post';

export default function App() {
  const [page, setPage] = useState<Page>('home');

  return (
    <div className="bg-[#f6f6f3] text-[#1c1c1a] min-h-screen">
      <TopNavBar />
      <div className="flex pt-24 gap-0 max-w-[1920px] mx-auto">
        <LeftSideBar />
        {page === 'home' ? (
          <HomePage onReadPost={() => setPage('post')} />
        ) : (
          <PostDetailPage onBack={() => setPage('home')} />
        )}
        {page === 'home' && <RightSideBar />}
      </div>
      <MobileBottomNav />
    </div>
  );
}
