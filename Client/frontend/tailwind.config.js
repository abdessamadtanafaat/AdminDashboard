/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: { transitionDuration: {
      '2000': '2000ms',
    }},
  },
  plugins: [require('@tailwindcss/typography'), require('daisyui') , require('tailwind-scrollbar')],
  daisyui: {
    themes: ['nord', 'dracula'],
  }

};