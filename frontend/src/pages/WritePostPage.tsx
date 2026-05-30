import {useEffect, useState} from 'react';
import MDEditor from '@uiw/react-md-editor';
import '@uiw/react-md-editor/markdown-editor.css';
import '@uiw/react-markdown-preview/markdown.css';
import { createPost , updatePost, fetchPost } from '../api/posts';

interface WritePostPageProps {
    onBack: () => void;
    editPostId?:number;
}

export default function WritePostPage({ onBack,editPostId }: WritePostPageProps) {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        if (!editPostId) return;
        fetchPost(editPostId)
            .then((res) => {
                setTitle(res.data.title);
                setContent(res.data.content);
            });
    }, [editPostId]);

    const handleSubmit = async () => {
        if (!title.trim() || !content.trim()) return;
        setSubmitting(true);
        try {
            editPostId
                ? await updatePost(editPostId, { title, content })
                : await createPost({ title, content });
            onBack();
        } catch {
            alert('게시글 작성에 실패했습니다.');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <main className="flex-1 min-w-0 pb-20 px-12">
            <button
                onClick={onBack}
                className="mb-8 inline-flex items-center gap-1.5 text-sm font-bold text-[#5c6e78] hover:text-[#4a5a63] transition-colors"
            >
                <span className="material-symbols-outlined text-[18px]">arrow_back</span>
                목록으로
            </button>

            <input
                type="text"
                placeholder="제목을 입력하세요"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                className="w-full text-4xl font-black text-[#1c1c1a] tracking-tighter bg-transparent border-none outline-none placeholder:text-[#c8c8c5] mb-6"
            />

            <div className="h-px bg-gradient-to-r from-transparent via-[#c8c8c5]/60 to-transparent mb-6" />

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
                    className="px-8 py-3 bg-[#5c6e78] text-white font-bold rounded-full hover:bg-[#4a5a63] transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                    {submitting ? '저장 중...' : editPostId ? '수정하기' : '게시하기'}
                </button>
            </div>
        </main>
    );
}
