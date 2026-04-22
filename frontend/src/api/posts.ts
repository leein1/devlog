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

export const fetchPostList = (cursor?: number, size = 10) =>
     apiClient.get<PostResponse[]>( '/posts',{params: {cursor, size},});

export interface TagResponse {
    id: number;
    name: string;
}

export const fetchPost = (id: number) =>
    apiClient.get<PostResponse>( `/posts/${id}`);

export const fetchPostTags = (postId: number) =>
    apiClient.get<TagResponse[]>(`/posts/${postId}/tags`);