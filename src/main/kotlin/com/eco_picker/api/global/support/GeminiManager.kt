package com.eco_picker.api.global.support

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class GeminiManager {
    fun generateNewsletter(category: NewsletterCategory): Newsletter {
        /*
        prompt 예시

        [공통 prompt 요청 내용]

        응답 형식 json 이고 영어로 응답해 그리고 json field 는 title, content, category, publishedAt, source 이고
        각각의 의미는 아래와 같아
        title: 최대 40자
        content: 최대 500자
        category: 뉴스, 교육용 자료, 이벤트 중 1개이며 실제 결과값은 "NEWS", "EDUCATION" ,"EVENT" 중 하나 일 것
        publishedAt: 뉴스, 교육용 자료, 이벤트의 출처에 기제된 글의 생성 일자 (예시: 2023-10-27 15:30:40)
        source: 출처 URL

        모든 정보의 기준은 오늘 기준 가장 최신 데이터를 가져오고 출처(source)가 불분명한 정보는 주지마, 출처를 너가 임의로 만들지마
        모든 정보는 필수 값이야.
        또한 모든 정보는 캐나다, 한국를 베이스로 만들어

        [NEWS prompt 요청 내용]

        재활용, 환경에 대한 오늘자 기준 최신 뉴스가 있다면 그 내용을 위에 내가 요청한 포멧이 맞춰서 총 N 개 만들어.

        [EDUCATION prompt 요청 내용]

         교육 대상은 어린 아이부터 성인인 재활용, 환경에 대한 고육용 컨텐츠를 만들어.
         그 예시로는 쓰레기 재활용 가이드나 재활용된 이후의 쓰레기는 어떤식으로 사용되는가와 같은 정보야.
         또한 내 요청사항에 맞춰서 내가 요청한 포멧에 맞춰서 총 N 개 만들어.

        [EVENT prompt 요청 내용]

        오늘자 기준 가족, 개인, 친구들끼리 참여할 수 있는 온라인, 오프라인 이벤트 정보를 내가 요청한 포멧이 맞춰 N 개 만들어.

        */
        //  json 으로 응답받으면 parsing 한 뒤 length 체크 후 Newsletter 생성하기,
        //  글자수 제한했지만 간헐적으로 더 길게 생성해서 줄 때가 있음
        return Newsletter(
            id = 1L,
            title = "",
            content = "",
            category = NewsletterCategory.NEWS,
            publishedAt = ZonedDateTime.now(),
            source = ""
        )
    }

    fun analyzeImage(): String {
        return "result"
    }
}