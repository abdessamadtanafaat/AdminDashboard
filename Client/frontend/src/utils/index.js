import axios from "axios";
const URL = "http://127.0.0.1:8080/";

export const customFetch = axios.create({
    baseURL : URL
})