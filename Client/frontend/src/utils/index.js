import axios from "axios";
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