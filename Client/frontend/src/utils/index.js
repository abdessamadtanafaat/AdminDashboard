import axios from "axios";
import {star1 , star2 ,star3 , star4 , star5 , face1 , face2 , face3 , face5 , face4} from '../assets'

const URL = "http://127.0.0.1:8080/";

export const customFetch = axios.create({
    baseURL : URL
})

export function formatDate(inputDate) {
    const dateParts = inputDate.split('/');
    const year = parseInt(dateParts[0]);
    const month = parseInt(dateParts[1]);
    const day = parseInt(dateParts[2]);
  
    const date = new Date(year, month - 1, day); // Month is zero-based
  
    const monthName = date.toLocaleString('default', { month: 'short' }); // Get abbreviated month name
    const monthNumber = date.getMonth() + 1; // Get month number
  
    return `Mo ${monthNumber}`;
  }
export const stars = [
    { url:star1 , value: 1 },
    { url: star2, value: 2 },
    { url:star3, value: 3 },
    { url: star4, value: 4 },
    { url: star5, value: 5 },
];
  
export const faces = [
    { url: face1, value: 1 },
    { url:face2, value: 2 },
    { url:face3, value: 3 },
    { url:face4, value: 4 },
    { url: face5, value: 5 },
  ];