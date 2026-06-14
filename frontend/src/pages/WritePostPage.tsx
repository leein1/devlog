import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import MDEditor from '@uiw/react-md-editor';
import '@uiw/react-md-editor/markdown-editor.css';
import '@uiw/react-markdown-preview/markdown.css';
import {
  createPost,
  updatePost,
  fetchPost,
  fetchPostTags,
  fetchPostSeries,
  fetchAllCategories,
  fetchAllTags,
  fetchAllSeries,
  type CategoryResponse,
  type TagResponse,
  type SeriesResponse,
} from '../api/posts';

export default function WritePostPage() {
  const { id } = useParams<{ id: string }>();
  const editPostId = id ? Number(id) : undefined;
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const [categoryId, setCategoryId] = useState<number | undefined>(undefined);
  const [tagIdList, setTagIdList] = useState<number[]>([]);
  const [seriesId, setSeriesId] = useState<number | undefined>(undefined);

  const [categories, setCategories] = useState<CategoryResponse[]>([]);
  const [tags, setTags] = useState<TagResponse[]>([]);
  const [seriesList, setSeriesList] = useState<SeriesResponse[]>([]);

  useEffect(() => {
    fetchAllCategories().then((res) => setCategories(res.data));
    fetchAllTags().then((res) => setTags(res.data));
    fetchAllSeries().then((res) => setSeriesList(res.data));
  }, []);

  useEffect(() => {
    if (!editPostId) return;
    fetchPost(editPostId).then((res) => {
      setTitle(res.data.title);
      setContent(res.data.content);
      setCategoryId(res.data.categoryId);
    });
    fetchPostTags(editPostId).then((res) => {
      setTagIdList(res.data.map((t) => t.id));
    });
    fetchPostSeries(editPostId).then((res) => {
      if (res.data.length > 0) setSeriesId(res.data[0].id);
    });
  }, [editPostId]);

  const handleSubmit = async () => {
    if (!title.trim() || !content.trim()) return;
    setSubmitting(true);
    try {
      if (editPostId) {
        await updatePost(editPostId, { title, content, categoryId, seriesId, tagIdList });
        navigate(`/post/${editPostId}`);
      } else {
        const res = await createPost({ title, content, categoryId, seriesId, tagIdList });
        navigate(`/post/${res.data.id}`);
      }
    } catch {
      alert('게시글 작성에 실패했습니다.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <main className="flex-1 min-w-0 pb-20 px-12">
      <button
        onClick={() => navigate('/')}
        className="mb-8 inline-flex items-center gap-1.5 text-sm font-bold text-primary hover:text-primary-container transition-colors"
      >
        <span className="material-symbols-outlined text-[18px]">arrow_back</span>
        목록으로
      </button>

      <input
        type="text"
        placeholder="제목을 입력하세요"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full text-4xl font-black text-on-surface tracking-tighter bg-transparent border-none outline-none placeholder:text-outline-variant mb-6"
      />

      <div className="h-px bg-gradient-to-r from-transparent via-outline-variant/60 to-transparent mb-6" />

      {/* 카테고리 / 태그 / 시리즈 선택 */}
      <div className="flex flex-col gap-4 mb-6 p-5 glass-card rounded-3xl">
        <div className="flex items-center gap-3">
          <span className="text-sm font-bold text-primary w-16 shrink-0">카테고리</span>
          <select
            value={categoryId ?? ''}
            onChange={(e) => setCategoryId(e.target.value ? Number(e.target.value) : undefined)}
            className="text-sm text-on-surface bg-transparent border border-outline-variant rounded-full px-3 py-1 outline-none focus:border-primary"
          >
            <option value="">선택 안함</option>
            {categories.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </select>
        </div>

        {/* 태그 — 클릭 토글 */}
        <div className="flex items-start gap-3">
          <span className="text-sm font-bold text-primary w-16 shrink-0 pt-1">태그</span>
          <div className="flex flex-wrap gap-2">
            {tags.length === 0 ? (
              <span className="text-xs text-outline">등록된 태그가 없습니다</span>
            ) : (
              tags.map((tag) => {
                const selected = tagIdList.includes(tag.id);
                return (
                  <button
                    key={tag.id}
                    type="button"
                    onClick={() =>
                      setTagIdList((prev) =>
                        selected ? prev.filter((id) => id !== tag.id) : [...prev, tag.id]
                      )
                    }
                    className={`px-3 py-1 rounded-full text-xs font-bold transition-colors ${
                      selected
                        ? 'bg-primary text-on-primary'
                        : 'bg-surface-container-highest text-primary hover:bg-surface-container-high'
                    }`}
                  >
                    {tag.name}
                  </button>
                );
              })
            )}
          </div>
        </div>

        {/* 시리즈 */}
        <div className="flex items-center gap-3">
          <span className="text-sm font-bold text-primary w-16 shrink-0">시리즈</span>
          <select
            value={seriesId ?? ''}
            onChange={(e) => setSeriesId(e.target.value ? Number(e.target.value) : undefined)}
            className="text-sm text-on-surface bg-transparent border border-outline-variant rounded-full px-3 py-1 outline-none focus:border-primary"
          >
            <option value="">선택 안함</option>
            {seriesList.map((s) => (
              <option key={s.id} value={s.id}>
                {s.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div data-color-mode="light">
        <MDEditor
          value={content}
          onChange={(val) => setContent(val ?? '')}
          height={560}
          preview="live"
        />
      </div>

      <div className="flex justify-end mt-6">
        <button
          onClick={handleSubmit}
          disabled={submitting || !title.trim() || !content.trim()}
          className="px-8 py-3 bg-primary text-on-primary font-bold rounded-full hover:bg-primary-container transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {submitting ? '저장 중...' : editPostId ? '수정하기' : '게시하기'}
        </button>
      </div>
    </main>
  );
}
