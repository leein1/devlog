import {useEffect, useState} from "react";
import { fetchPost, fetchPostTags, type PostResponse, type TagResponse } from '../api/posts';


interface PostDetailPageProps {
  postId: number | null;
  onBack: () => void;
}

export default function PostDetailPage({ postId, onBack }: PostDetailPageProps) {

  const [post, setPost] = useState<PostResponse | null >(null);
  const [tags, setTags] = useState<TagResponse[]>([]);

  useEffect(() => {
    if (!postId) return;
    fetchPost(postId).then((res) => {setPost(res.data);})
    fetchPostTags(postId).then((res) => {setTags(res.data);})
  },[postId]);

  const formatDate = (dateStr: string) =>
      new Date(dateStr).toLocaleDateString('en-US', { year: 'numeric',
        month: 'short', day: 'numeric' });

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
              {post?.categoryName}
            </span>
            <span className="w-1 h-1 rounded-full bg-[#c8c8c5]" />
            <time className="text-xs font-medium text-[#797976]">{post ? formatDate(post.createdAt) : ''}</time>
          </div>

          <h1 className="text-5xl font-black text-[#1c1c1a] tracking-tighter leading-[1.1]">
            {post?.title}
          </h1>

          {/* 태그 */}
          <div className="flex flex-wrap gap-2 pt-1">
            {tags.map((tag) => (
              <span
                key={tag.id}
                className="glass-card px-4 py-1.5 rounded-full text-sm font-semibold text-[#494946]"
              >
                #{tag.name}
              </span>
            ))}
          </div>
        </header>

        {/* 구분선 */}
        <div className="h-px bg-gradient-to-r from-transparent via-[#c8c8c5]/60 to-transparent mb-10" />

        {/* 본문 콘텐츠 */}
        <div className="prose-content text-[#1c1c1a]">
          <p className="text-[#494946] leading-relaxed font-medium whitespace-pre-wrap">
            {post?.content}
          </p>
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
          </div>
          <div className="glass-card rounded-2xl p-5 group cursor-pointer hover:border-[#5c6e78]/25 transition-all text-right">
            <p className="text-xs font-bold text-[#797976] tracking-widest uppercase mb-2 flex items-center gap-1 justify-end">
              다음 글
              <span className="material-symbols-outlined text-[14px]">arrow_forward</span>
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
        </div>
      </aside>
    </main>
  );
}
