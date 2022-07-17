package com.mac.shorten.interactors.link

import com.google.gson.GsonBuilder
import com.mac.shorten.cache.AppDatabaseFake
import com.mac.shorten.cache.LinkDaoFake
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import com.mac.shorten.network.LinkService
import com.mac.shorten.network.data.MockWebServerResponses
import com.mac.shorten.network.data.MockWebServerResponses.linkResponse
import com.mac.shorten.network.model.LinkDtoMapper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ShortenLinkTest {

    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val DUMMY_URL = "helloworld.com"

    private lateinit var shortenLink: ShortenLink

    private lateinit var linkService: LinkService
    private lateinit var linkDao: LinkDaoFake
    private val entityMapper = LinkEntityMapper()
    private val dtoMapper = LinkDtoMapper()

    @Before
    fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/v2/")
        linkService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(LinkService::class.java)
        linkDao = LinkDaoFake(appDatabaseFake = appDatabase)

        shortenLink = ShortenLink(
            linkDao = linkDao,
            entityMapper = entityMapper,
            linkService = linkService,
            linkDtoMapper = dtoMapper
        )
    }

    @Test
    fun shortenLinkFromNetwork_emitLinksFromCache(): Unit = runBlocking {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(linkResponse)
        )

        // confirm the cache is empty to start
        assert(linkDao.getAllLinks().isEmpty())

        // run use case
        val flowItems = shortenLink.run(DUMMY_URL).toList()

        // confirm the cache is no longer empty
        assert(linkDao.getAllLinks().isNotEmpty())

        // first emission should be 'loading'
        assert(flowItems[0].status == DataState.Status.LOADING)

        // second emission should be the list of links
        val links = flowItems[1].data
        assert((links?.size ?: 0) > 0)

        // confirm they are actually Link objects
        assert(value = links?.get(index = 0) is Link)

        // confirm loading is false now
        assert(flowItems[1].status != DataState.Status.LOADING)
    }

    @Test
    fun shortenLinkFromNetwork_emitHttpError(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )
        val flowItems = shortenLink.run(DUMMY_URL).toList()

        // first emission should be 'loading'
        assert(flowItems[0].status == DataState.Status.LOADING)

        // second emission should be the exception
        val error = flowItems[1].error
        assert(error != null)

        // confirm loading is false now
        assert(flowItems[1].status != DataState.Status.LOADING)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}