# Introduction
This assessment was done for Tribe Hired.

# Usage
1. Install packages.
```
npm install
```

2. Run the application.
```
node main.js
```

Two endpoints were created for the assessment.
1. Get top posts, which can be filtered with n posts.

| Parameter         | Type              |
| -------------     |:-------------:    |
| n                 | int               |
```
curl -X GET https://localhost:3000/top-posts?n=1
```

2. Search comments with filters.
Filters available for all fields, including:


| Parameter         | Type              |
| -------------     |:-------------:    |
| postId            | int               |
| id                | int               |
| name              | string            |
| email             | string            |
| body              | string            |

```
curl -X GET https://localhost:3000/comments?postId=2&id=1
```


# Assessment Question
## Build a REST API


1. Return a list of Top Posts ordered by their number of Comments. 

Consume the API endpoints provided: 

	- comments endpoint – https://jsonplaceholder.typicode.com/comments
	-  View Single Post endpoint – https://jsonplaceholder.typicode.com/posts/{post_id}
	-  View All Posts endpoint – https://jsonplaceholder.typicode.com/posts
	

Your API response should have the following fields: 

		- post_id 
		- post_title
		- post_body 
		- total_number_of_comments


2. Search API 
Create an endpoint that allows a user to filter the comments based on all the available fields. Your solution needs to be scalable. 
	- comments endpoint – https://jsonplaceholder.typicode.com/comments



