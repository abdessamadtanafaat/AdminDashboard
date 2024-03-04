import axios from "axios";
const URL = 'https://localhost:8080';

export const customFetch = axios.create({
    baseURL : URL
})