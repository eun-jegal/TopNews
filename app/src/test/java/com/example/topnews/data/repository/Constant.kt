package com.example.topnews.data.repository

import com.example.topnews.data.model.Article
import com.example.topnews.data.model.News
import com.example.topnews.data.model.Source
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

val testArticle = Article(
    id = 1,
    source = Source(id = "cnn", name = "CNN"),
    author = "Nathaniel Meyersohn",
    description = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.",
    title = "Camp toy store pleads for help after Silicon Valley Bank collapse - CNN",
    url = "https://www.cnn.com/2023/03/10/business/camp-toy-store-svb-bank/index.html",
    urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230310161851-camp-store-file-restricted.jpg?c=16x9&q=w_800,c_fill",
    publishedAt = "2023-03-11T16:21:00Z",
    content = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.\r\nCamp, a venture-backed retailer, sent an email … [+1685 chars]",
)

val testArticle2 = Article(
    id = 1,
    source = Source(id = "cnn", name = "CNN"),
    author = "Nathaniel Meyersohn",
    description = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.",
    title = "Camp toy store pleads for help after Silicon Valley Bank collapse - CNN",
    url = "https://www.cnn.com/2023/03/10/business/camp-toy-store-svb-bank/index.html",
    urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230310161851-camp-store-file-restricted.jpg?c=16x9&q=w_800,c_fill",
    publishedAt = "2023-03-11T16:21:00Z",
    content = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.\r\nCamp, a venture-backed retailer, sent an email … [+1685 chars]",
)

val testArticle3 = Article(
    id = 1,
    source = Source(id = "cnn", name = "CNN"),
    author = "Nathaniel Meyersohn",
    description = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.",
    title = "Camp toy store pleads for help after Silicon Valley Bank collapse - CNN",
    url = "https://www.cnn.com/2023/03/10/business/camp-toy-store-svb-bank/index.html",
    urlToImage = "https://media.cnn.com/api/v1/images/stellar/prod/230310161851-camp-store-file-restricted.jpg?c=16x9&q=w_800,c_fill",
    publishedAt = "2023-03-11T16:21:00Z",
    content = "A toy company based in New York has gotten caught up in the collapse of Silicon Valley Bank and is pleading with customers for help keeping it afloat.\r\nCamp, a venture-backed retailer, sent an email … [+1685 chars]",
)

val testArticleList = listOf(
    testArticle,
    testArticle2,
    testArticle3
)

val successResponse: Response<News> = Response.success(
    News(
        articles = testArticleList,
        status = "ok",
        totalResults = 20
    )
)

val errorResponse: Response<News> = Response.error(
    404,
    "{\"key\":[\"somestuff\"]}"
        .toResponseBody("application/json".toMediaTypeOrNull())
)