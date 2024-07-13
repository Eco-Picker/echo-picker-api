package com.eco_picker.api.global.filter
import jakarta.servlet.Filter
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.FilterChain
import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.UUID

@Component
class TraceIdFilter : Filter {
    companion object {
        const val TRACE_ID = "traceId"
    }
    // @todo logback config
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val traceId = UUID.randomUUID().toString()
        MDC.put(TRACE_ID, traceId)
        try {
            chain.doFilter(request, response)
        } finally {
            MDC.remove(TRACE_ID)
        }
    }
}