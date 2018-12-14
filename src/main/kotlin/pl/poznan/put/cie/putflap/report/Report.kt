package pl.poznan.put.cie.putflap.report

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class Report protected constructor() {

    companion object {

        private val mapper = ObjectMapper()

        init {
            mapper.registerModule(KotlinModule())
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
        }

        fun getJSON(report: Report): String {
            return mapper.writeValueAsString(report)
        }
    }
}