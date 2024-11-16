# Use an official Node runtime as a parent image
FROM node:alpine

# Set working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the container
COPY . .

# Build the React app for production
RUN npm run build

# Expose port React app runs on
EXPOSE 3000

# Command to serve the React app
CMD ["npm", "start"]
