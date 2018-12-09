package pl.poznan.put.cie.putflap.report

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class Report protected constructor() {
    companion object {
        fun getJSON(report: Report): String {
            val mapper = ObjectMapper()
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            return mapper.writeValueAsString(report)
        }
    }
}