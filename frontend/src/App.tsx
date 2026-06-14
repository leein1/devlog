import TopNavBar from './components/layout/TopNavBar';
import LeftSideBar from './components/layout/LeftSideBar';
import RightSideBar from './components/layout/RightSideBar';
import MobileBottomNav from './components/layout/MobileBottomNav';
import HomePage from './pages/HomePage';
import PostDetailPage from './pages/PostDetailPage';
import WritePostPage from './pages/WritePostPage';
import {Route, Routes} from "react-router-dom";

// type Page = 'home' | 'post' | 'write';

export default function App() {
  // const [page, setPage] = useState<Page>('home');
  // const [selectedPostId, setSelectedPostId] = useState<number | null>(null);

  return (
    <div className="bg-[#f6f6f3] text-[#1c1c1a] min-h-screen">
      {/*<TopNavBar onWrite={() => { setSelectedPostId(null); setPage('write'); }} />*/}
      {/*<div className="flex pt-24 gap-0 max-w-[1920px] mx-auto">*/}
      {/*  <LeftSideBar />*/}
      {/*  {page === 'home' && (*/}
      {/*      <HomePage onReadPost={(id) => { setSelectedPostId(id); setPage('post'); }} />*/}
      {/*  )}*/}
      {/*  {page === 'post' && (*/}
      {/*      <PostDetailPage*/}
      {/*          postId={selectedPostId}*/}
      {/*          onBack={() => setPage('home')}*/}
      {/*          onEdit={(id) => { setSelectedPostId(id); setPage('write'); }}*/}
      {/*      />*/}
      {/*  )}*/}
      {/*  {page === 'write' && (*/}
      {/*      <WritePostPage onBack={() => setPage('home')} editPostId={selectedPostId ?? undefined} />*/}
      {/*  )}*/}
      {/*  {page === 'home' && <RightSideBar />}*/}
      {/*</div>*/}
      {/*<MobileBottomNav />*/}

        <TopNavBar />
        <div className="flex pt-24 gap-0 max-w-[1920px] min-h-screen]">
            <LeftSideBar />
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/post/:id" element={<PostDetailPage />}/>
                <Route path="/write" element={<WritePostPage/>}/>
                <Route path="/write/:id" element={<WritePostPage />}/>
                {/*<Route path="/portfolio" element={<PortFolioPage/>}/>*/}
            </Routes>
            {location.pathname === '/' && <RightSideBar />}
        </div>
        <MobileBottomNav />
    </div>
  );
}


