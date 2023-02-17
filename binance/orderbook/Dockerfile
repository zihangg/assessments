# Use the official Node.js image as the base image
FROM node:16.4-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the package.json and package-lock.json files to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Expose port 3000 to the host
EXPOSE 3001

# Start the application
CMD ["npm", "run", "start"]