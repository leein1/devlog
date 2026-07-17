// const recentPosts = [
//   {
//     id: 1,
//     category: 'Frontend',
//     date: 'Oct 24, 2023',
//     title: 'Implementing Framer Motion for Editorial Flow',
//     excerpt:
//       'A deep dive into creating natural, liquid transitions between layout states without breaking the user\'s mental model.',
//   },
//   {
//     id: 2,
//     category: 'React',
//     date: 'Oct 18, 2023',
//     title: 'Mastering Server Components in Next.js',
//     excerpt:
//       'Reducing client-side shipping by leveraging the power of streaming and selective hydration in the latest app directory patterns.',
//   },
// ];

import { useEffect, useState } from 'react';
import { fetchPostList, type PostResponse } from '../api/posts.ts';
import { useNavigate } from 'react-router-dom';

// interface HomePageProps {
//   onReadPost: (id:number) => void;
// }

export default function HomePage() {
  const navigate = useNavigate();
  const [posts, setPosts] = useState<PostResponse[]>([]);
  const [error, setError] = useState(false);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchPostList(page)
      .then((res) => {
        setPosts(res.data.content);
        setTotalPages(res.data.totalPages);
      })
      .catch(() => setError(true));
  }, [page]);

  const featuredPost = posts[0];
  const recentPosts = posts.slice(1);

  const formatDate = (dateStr: string) =>
    new Date(dateStr).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });

  const truncate = (text: string, max = 120) =>
    text.length > max ? text.slice(0, max) + '...' : text;

  if (error)
    return (
      <main className="flex-1 min-w-0 pb-20 px-12 flex items-center justify-center">
        <p className="text-outline font-medium">게시글을 불러올 수 없습니다</p>
      </main>
    );

  return (
    <main className="flex-1 min-w-0 pb-20 px-12">
      {/* Featured Post */}
      <section className="mb-12">
        <div className="relative group overflow-hidden rounded-3xl glass-panel aspect-[21/9] flex items-center">
          {/* 배너 내부 미세 그라디언트 — 좌측에서 우측으로 페이드 */}
          <div className="absolute inset-0 bg-gradient-to-r from-surface-container-low/80 via-surface-container/50 to-transparent" />
          {/* 우측 장식 — 흐릿한 원 */}
          <div className="absolute right-16 top-1/2 -translate-y-1/2 pointer-events-none">
            <div className="w-56 h-56 rounded-full bg-gradient-to-br from-primary/10 to-primary/5 blur-2xl" />
          </div>
          {/* specular highlight */}
          <div className="absolute top-0 left-0 right-0 h-px bg-gradient-to-r from-transparent via-surface-container-lowest/80 to-transparent" />

          <div className="relative z-10 p-12 max-w-2xl space-y-6">
            <span className="inline-flex items-center gap-1.5 bg-primary/8 text-primary px-4 py-1.5 rounded-full text-xs font-bold tracking-widest uppercase border border-primary/15">
              <span
                className="material-symbols-outlined text-[14px]"
                style={{ fontVariationSettings: "'FILL' 1" }}
              >
                star
              </span>
              Featured Post
            </span>
            <h1 className="text-5xl font-black text-on-surface tracking-tighter leading-[1.1]">
              {featuredPost ? featuredPost.title : '게시글을 불러오는 중...'}
            </h1>
            <p className="text-on-surface-variant text-lg leading-relaxed font-medium">
              {featuredPost && truncate(featuredPost.content)}
            </p>
            <div className="pt-2">
              <button
                // onClick={onReadPost}
                onClick={() => featuredPost && navigate(`/post/${featuredPost.id}`)}
                className="bg-primary text-on-primary px-8 py-4 rounded-full font-bold flex items-center gap-2 group/btn transition-all hover:bg-primary-container active:scale-95 shadow-[0_8px_24px_rgba(92,110,120,0.28)]"
              >
                Read the Full Story
                <span className="material-symbols-outlined group-hover/btn:translate-x-1 transition-transform">
                  arrow_forward
                </span>
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* Recent Posts */}
      <div className="space-y-1">
        {recentPosts.map((post) => (
          <article
            key={post.id}
            className="group cursor-pointer"
            onClick={() => navigate(`/post/${post.id}`)}
          >
            <div className="p-8 rounded-2xl transition-all hover:glass-card space-y-4">
              <div className="flex items-center gap-3">
                <span className="text-xs font-bold text-primary tracking-widest uppercase">
                  {post.categoryName}
                </span>
                <span className="w-1 h-1 rounded-full bg-outline-variant" />
                <time className="text-xs font-medium text-outline">
                  {' '}
                  {formatDate(post.createdAt)}{' '}
                </time>
              </div>
              <h2 className="text-3xl font-extrabold tracking-tight text-on-surface group-hover:text-primary transition-colors leading-tight">
                {post.title}
              </h2>
              <p className="text-on-surface-variant font-medium leading-relaxed">
                {truncate(post.content)}
              </p>
              <div className="pt-1">
                <span className="inline-flex items-center gap-1 text-sm font-bold text-primary opacity-0 group-hover:opacity-100 transition-opacity">
                  Read more
                  <span className="material-symbols-outlined text-[16px] group-hover:translate-x-0.5 transition-transform">
                    arrow_forward
                  </span>
                </span>
              </div>
            </div>
            <div className="h-px mx-8 bg-gradient-to-r from-transparent via-outline-variant/50 to-transparent" />
          </article>
        ))}
      </div>

      {/* Pagination */}
      <div className="flex items-center justify-center gap-4 mt-8">
        <button
          disabled={page === 0}
          onClick={() => setPage((p) => p - 1)}
          className="px-5 py-2.5 rounded-full font-bold text-sm text-on-surface-variant transition-all hover:glass-card disabled:opacity-40 disabled:pointer-events-none"
        >
          이전
        </button>
        <span className="text-xs font-medium text-outline">
          {totalPages === 0 ? 0 : page + 1} / {totalPages}
        </span>
        <button
          disabled={page + 1 >= totalPages}
          onClick={() => setPage((p) => p + 1)}
          className="px-5 py-2.5 rounded-full font-bold text-sm text-on-surface-variant transition-all hover:glass-card disabled:opacity-40 disabled:pointer-events-none"
        >
          다음
        </button>
      </div>
    </main>
  );
}
