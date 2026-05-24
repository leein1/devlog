import apiClient from "./client.ts";

export interface PostResponse {
    id: number;
    title: string;
    content: string;
    viewCount: number;
    categoryId: number;
    categoryName: string;
    createdAt: string;
    updatedAt: string;
}

export interface SeriesResponse{
    id: number;
    name: string;
}

export interface PostSeriesResponse{
    postId: number;
    title: string;
    orderNum: number;

}

export interface CommentResponse{
    id: number;
    content: string;
    userId: number;
    nickname: string;
    createdAt: string;
}

export interface CommentRequest{
    content: string;
    userId: number;
}


export const fetchComment = (postId: number, signal?: AbortSignal) =>
    apiClient.get<CommentResponse[]>(`/posts/${postId}/comments`, { signal });

export const createComment = (postId: number, data: CommentRequest) =>
    apiClient.post<CommentResponse>(`/posts/${postId}/comments`, data);

export const deleteComment=(postId:number, commentid:number) =>
    apiClient.delete(`/posts/${postId}/comments/${commentid}`);



export const fetchPostList = (cursor?: number, size = 10) =>
     apiClient.get<PostResponse[]>( '/posts',{params: {cursor, size},});

export interface TagResponse {
    id: number;
    name: string;
}

export const fetchPost = (id: number, signal?:AbortSignal) =>
    apiClient.get<PostResponse>( `/posts/${ id }`, { signal });

export const fetchPostTags = (postId: number, signal?:AbortSignal) =>
    apiClient.get<TagResponse[]>(`/posts/${ postId }/tags`,{ signal });

export interface PostRequest {
    title: string;
    content: string;
    categoryId?: number;
}


export const createPost = (data: PostRequest) =>
    apiClient.post<PostResponse>('/posts', data);

export const updatePost = (id: number, data: PostRequest) =>
    apiClient.put<PostResponse>(`/posts/${id}`, data);

export const deletePost = (id: number) =>
    apiClient.delete<PostResponse>(`/posts/${id}`);

// 게시글이 속한 시리즈 목록
export const fetchPostSeries = (postId: number, signal?:AbortSignal) =>
    apiClient.get<SeriesResponse[]>(`/posts/${postId}/series`,{ signal });

// 시리즈에 속한 게시글 목록
export const fetchSeriesPosts = (seriesId: number, signal?: AbortSignal) =>
    apiClient.get<PostSeriesResponse[]>(`/series/${seriesId}/posts`, { signal });

