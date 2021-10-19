package net.toregard.kotlinspringrdf4j.demo

import org.assertj.core.api.Assertions.assertThat
import org.eclipse.rdf4j.RDF4JException
import org.eclipse.rdf4j.model.ValueFactory
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.query.QueryResults
import org.eclipse.rdf4j.rio.RDFFormat
import org.eclipse.rdf4j.rio.Rio
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource

import java.io.InputStream




class RdfInitTest {

    @Test
    fun testMe(){
        val resource = ClassPathResource(
            "test.ttl"
        ).inputStream
       A().a(resource)
       assertThat(true).isTrue()
    }
}

class A {
    fun a(inputStream: InputStream ) {
        val filename = "test.ttl"
        //val inputStream = A::class.java.getResourceAsStream("/$filename")
        val model = Rio.parse(inputStream, "", RDFFormat.TURTLE)
        val vf: ValueFactory = SimpleValueFactory.getInstance()
        val uri = vf.createIRI("http://example.org/Picasso")
        val filter = model.filter { it.subject == uri }
            .groupBy { it.`object` }
        val baseURI = "http://example.org/test.ttl"
        val format = RDFFormat.TURTLE
        try {
            QueryResults.parseGraphBackground(inputStream, baseURI, format).let {

                while (it.hasNext()) {
                    val statement = it.next()
                    println(statement.subject.stringValue())
                }
            }
        } catch (e: RDF4JException) {
            // handle unrecoverable error
        } finally {
            inputStream.close()
        }
    }
}
