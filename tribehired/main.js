const express = require("express");
const app = express();

const COMMENTS_ENDPOINT = "https://jsonplaceholder.typicode.com/comments";
const ALL_POST_ENDPOINT = "https://jsonplaceholder.typicode.com/posts";

const blacklist = [2, 5];

// GET endpoint that returns the top n posts by number of comments
app.get("/top-posts", async (req, res) => {
  const n = req.query.n;

  const comments = await fetch(COMMENTS_ENDPOINT).then((res) => res.json());

  const totalComments = {};
  // use hashmap to track number of comments
  for (let i = 0; i < comments.length; i++) {
    if (totalComments[comments[i].postId] !== undefined) {
      totalComments[comments[i].postId] = totalComments[comments[i].postId] + 1;
    } else {
      totalComments[comments[i].postId] = 1;
    }
  }

  const posts = await fetch(ALL_POST_ENDPOINT).then((res) => res.json());
  const response = [];

  // add number of comments to posts
  for (let i = 0; i < posts.length; i++) {
    // build res object
    const obj = {
      post_id: posts[i].id,
      post_title: posts[i].title,
      post_body: posts[i].body,
      number_of_comments: totalComments[posts[i].id],
    };

    response[i] = obj;
  }

  res.status(200).send(response.slice(0, n));
});

// GET endpoint for comments filter
app.get("/comments", async (req, res) => {
  const params = {
    postId: parseInt(req.query.postId),
    id: parseInt(req.query.id),
    name: req.query.name,
    email: req.query.email,
    body: req.query.body,
  };

  // drop undefined or NaN keys
  Object.keys(params).forEach((key) => {
    if (params[key] === undefined || Number.isNaN(params[key])) {
      delete params[key];
    }
  });

  // get comments
  const comments = await fetch(COMMENTS_ENDPOINT).then((res) => res.json());

  // function to filter
  const filterBy = (data, filters) => {
    return data.filter((item) => {
      let matches = true;
      for (const [key, value] of Object.entries(filters)) {
        if (item[key] !== value) {
          matches = false;
          break;
        }
      }
      return matches;
    });
  };

  const filtered = filterBy(comments, params);

  filtered = filtered.filter((item) => {
    return !blacklist.includes(item.id);
  });

  res.status(200).send(filtered);
});

const port = 3000;

app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});
