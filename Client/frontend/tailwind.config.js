/** @type {import('tailwindcss').Config} */
import typography from '@tailwindcss/typography';
import daisyui from 'daisyui';
import tailwindScrollbar from 'tailwind-scrollbar';

export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      transitionDuration: {
        '2000': '2000ms',
      },
    },
  },
  plugins: [typography, daisyui, tailwindScrollbar],
  daisyui: {
    themes: ['nord', 'dracula'],
  },
};
