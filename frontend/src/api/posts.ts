import apiClient from "./client.ts";

export interface PostResponse{
    id: number;
    title: string;
    content: string;
    viewCount: number;
    categoryId: number;
    categoryName: string;
    createdAt: string;
    updatedAt: string;
}

export const fetchPosts = (cursor?: number, size = 10) =>{
    return apiClient.get<PostResponse[]>( '/posts',{
        params: {cursor, size},
        }
    );
};