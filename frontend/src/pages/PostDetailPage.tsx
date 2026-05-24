import {useEffect, useMemo, useState} from "react";
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';
import {
  fetchPost,
  fetchPostTags,
  fetchSeriesPosts,
  type PostResponse,
  type SeriesResponse,
  type TagResponse,
  type PostSeriesResponse,
  fetchPostSeries, deletePost,
  fetchComment, createComment, deleteComment,
  type CommentResponse,
} from '../api/posts';


const slugify = (text: string) =>
  text.toLowerCase().replace(/\s+/g, '-').replace(/[^\w-]/g, '');

interface PostDetailPageProps {
  postId: number | null;
  onBack: () => void;
  onEdit: (id: number) => void;
}

export default function PostDetailPage({ postId, onBack, onEdit }: PostDetailPageProps) {

  const [post, setPost] = useState<PostResponse | null>(null);
  const [tags, setTags] = useState<TagResponse[]>([]);
  const [error, setError] = useState(false);
  const [series, setSeries] = useState<SeriesResponse | null>(null);
  const [seriesPosts, setSeriesPosts] = useState<PostSeriesResponse[]>([]);
  const [comments, setComments] = useState<CommentResponse[]>([]);
  const [commentText, setCommentText] = useState('');

  useEffect(() => {
    if (!postId) return;
    const controller = new AbortController();

    fetchPost(postId, controller.signal)
        .then((res) => { setPost(res.data); })
        .catch((err) => { if (err?.code !== 'ERR_CANCELED') setError(true); });

    fetchPostTags(postId, controller.signal)
        .then((res) => { setTags(res.data); })
        .catch((err) => { if (err?.code !== 'ERR_CANCELED') setError(true); });

    return () => controller.abort();
  }, [postId]);

  useEffect(() => {
    if (!postId) return;
    const controller = new AbortController();

    fetchPostSeries(postId, controller.signal)
        .then((res) => {
          if (res.data.length === 0) return;
          const firstSeries = res.data[0];
          setSeries(firstSeries);
          return fetchSeriesPosts(firstSeries.id, controller.signal);
        })
        .then((res) => {
          if (res) setSeriesPosts(res.data);
        })
        .catch((err) => {
          if (err?.code !== 'ERR_CANCELED') setError(true);
        });

    return () => controller.abort();
  }, [postId]);

  useEffect(() => {
    if (!postId) return;
    const controller = new AbortController();

    fetchComment(postId, controller.signal)
        .then((res) => setComments(res.data))
        .catch((err) => { if (err?.code !== 'ERR_CANCELED') setError(true); });

    return () => controller.abort();
  }, [postId]);


  if (error) return (
    <main className="flex-1 min-w-0 pb-20 px-12 flex items-center justify-center">
      <p className="text-[#797976] font-medium">
        게시글을 불러올 수 없습니다
      </p>
    </main>
  );

  const handleDelete = async () => {
    if (!postId || !window.confirm("게시글을 삭제하시겠습니까?")) return;
    await deletePost(postId);
    onBack();
  };

  const headings = useMemo(() =>
    (post?.content ?? '').split('\n')
      .filter(line => /^#{1,6}\s/.test(line))
      .map(line => ({
        level: line.match(/^(#+)/)?.[1].length ?? 1,
        text: line.replace(/^#+\s+/, ''),
        id: slugify(line.replace(/^#+\s+/, '')),
      })),
  [post?.content]);

  const formatDate = (dateStr: string) =>
    new Date(dateStr).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });

  return (
    <main className="flex-1 min-w-0 pb-20 px-12 flex gap-8">
      {/* 본문 */}
      <article className="flex-1 min-w-0">
        {/* 뒤로 가기 */}
        {/*<button*/}
        {/*  onClick={onBack}*/}
        {/*  className="mb-8 inline-flex items-center gap-1.5 text-sm font-bold text-[#5c6e78] hover:text-[#4a5a63] transition-colors"*/}
        {/*>*/}
        {/*  <span className="material-symbols-outlined text-[18px]">arrow_back</span>*/}
        {/*  목록으로*/}
        {/*</button>*/}
        <div className="mb-8 flex items-center justify-between">
          <button
            onClick={onBack}
            className="inline-flex items-center gap-1.5 text-sm font-bold text-[#5c6e78] hover:text-[#4a5a63] transition-colors"
          >
            <span className="material-symbols-outlined text-[18px]">arrow_back</span>
            목록으로
          </button>
          <div className="flex items-center gap-2">
            <button
              onClick={() => postId && onEdit(postId)}
              className="px-4 py-1.5 text-sm font-bold text-[#5c6e78] hover:text-[#4a5a63] transition-colors"
            >
              수정
            </button>
            <button
              onClick={handleDelete}
              className="px-4 py-1.5 text-sm font-bold text-red-400 hover:text-red-500 transition-colors"
            >
              삭제
            </button>
          </div>
        </div>

        {/* 헤더 */}
        <header className="mb-10 space-y-5">
          <div className="flex items-center gap-3">
            <span className="text-xs font-bold text-[#5c6e78] tracking-widest uppercase">
              {post?.categoryName}
            </span>
            <span className="w-1 h-1 rounded-full bg-[#c8c8c5]" />
            <time className="text-xs font-medium text-[#797976]">{post ? formatDate(post.createdAt) : ''}</time>
            <span className="w-1 h-1 rounded-full bg-[#c8c8c5]" />
            <span className="text-xs font-medium text-[#797976]">
              조회 {post?.viewCount ?? 0}
            </span>
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
        <div className="markdown-content">
          <ReactMarkdown
            remarkPlugins={[remarkGfm]}
            components={{
              h1: ({children}) => <h1 id={slugify(String(children))}>{children}</h1>,
              h2: ({children}) => <h2 id={slugify(String(children))}>{children}</h2>,
              h3: ({children}) => <h3 id={slugify(String(children))}>{children}</h3>,
              h4: ({children}) => <h4 id={slugify(String(children))}>{children}</h4>,
              h5: ({children}) => <h5 id={slugify(String(children))}>{children}</h5>,
              h6: ({children}) => <h6 id={slugify(String(children))}>{children}</h6>,
            }}
          >
            {post?.content ?? ''}
          </ReactMarkdown>
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

        {/* 구분선 */}
        <div className="h-px bg-gradient-to-r from-transparent via-[#c8c8c5]/60 to-transparent my-12" />

        {/* 댓글 섹션 */}
        <section>
          <h2 className="text-lg font-black text-[#1c1c1a] tracking-tight mb-6">
            댓글 {comments.length}
          </h2>

          {/* 댓글 작성 폼 */}
          <div className="glass-card rounded-2xl p-5 mb-8">
            <textarea
              value={commentText}
              onChange={(e) => setCommentText(e.target.value)}
              placeholder="댓글을 입력하세요"
              rows={3}
              className="w-full bg-transparent text-sm text-[#1c1c1a] placeholder-[#b0b0ad] resize-none outline-none"
            />
            <div className="flex justify-end mt-3">
              <button
                onClick={async () => {
                  if (!postId || !commentText.trim()) return;
                  const res = await createComment(postId, { content: commentText, userId: 1 });
                  setComments((prev) => [...prev, res.data]);
                  setCommentText('');
                }}
                className="px-5 py-2 rounded-full bg-[#5c6e78] text-white text-xs font-bold hover:bg-[#4a5a63] transition-colors"
              >
                등록
              </button>
            </div>
          </div>

          {/* 댓글 목록 */}
          <ul className="space-y-4">
            {comments.map((c) => (
              <li key={c.id} className="glass-card rounded-2xl p-5">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-sm font-bold text-[#1c1c1a]">{c.nickname}</span>
                  <div className="flex items-center gap-3">
                    <time className="text-xs text-[#797976]">
                      {new Date(c.createdAt).toLocaleDateString('ko-KR')}
                    </time>
                    <button
                      onClick={async () => {
                        if (!postId || !window.confirm('댓글을 삭제하시겠습니까?')) return;
                        await deleteComment(postId, c.id);
                        setComments((prev) => prev.filter((x) => x.id !== c.id));
                      }}
                      className="text-xs text-red-400 hover:text-red-500 font-bold transition-colors"
                    >
                      삭제
                    </button>
                  </div>
                </div>
                <p className="text-sm text-[#494946] leading-relaxed">{c.content}</p>
              </li>
            ))}
          </ul>
        </section>

      </article>

      {/* 목차 사이드바 (데스크톱) */}
      <aside className="hidden xl:flex flex-col gap-4 w-56 sticky top-28 self-start">
        {series && (
          <div className="glass-panel rounded-2xl p-5">
            <h3 className="text-xs font-black tracking-widest uppercase text-[#5c6e78] mb-4">
              시리즈 : {series.name}
            </h3>
            <ol className="space-y-2">
              {seriesPosts.map((sp) => (
                <li key={sp.postId} className="flex items-center gap-2">
                  <span className="text-xs text-[#5c6e78] font-bold w-4 shrink-0">{sp.orderNum}</span>
                  <span className={`text-xs leading-snug ${sp.postId === postId ? 'font-black text-[#1c1c1a]' : 'font-medium text-[#797976]'}`}>
                    {sp.postId === postId && '▶ '}{sp.title}
                  </span>
                </li>
              ))}
            </ol>
          </div>
        )}

        <div className="glass-panel rounded-2xl p-5">
          <h3 className="text-xs font-black tracking-widest uppercase text-[#5c6e78] mb-4">
            목차
          </h3>
          {headings.length > 0 && (
            <ul className="space-y-2">
              {headings.map((h) => (
                <li key={`${h.id}-${h.level}`} style={{ paddingLeft: `${(h.level - 1) * 12}px` }}>
                  <button
                    onClick={() => document.getElementById(h.id)?.scrollIntoView({ behavior: 'smooth' })}
                    className="text-xs text-[#797976] hover:text-[#1c1c1a] transition-colors leading-snug text-left w-full"
                  >
                    {h.text}
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      </aside>
    </main>
  );
}
