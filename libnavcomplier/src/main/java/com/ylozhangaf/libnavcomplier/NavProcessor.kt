package com.ylozhangaf.libnavcomplier

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.google.auto.service.AutoService
import com.ylozhangaf.libnavannotation.ActivityDestination
import com.ylozhangaf.libnavannotation.FragmentDestination
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation
import kotlin.math.abs

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(
    "com.ylozhangaf.libnavannotation.FragmentDestination",
    "com.ylozhangaf.libnavannotation.ActivityDestination"
)
class NavProcessor : AbstractProcessor() {

    private var messager: Messager? = null

    private var filer: Filer? = null

    companion object{
        const val OUTPUT_FILE_NAME = "destination.json"
    }

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        messager = processingEnv?.messager
        filer = processingEnv?.filer
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        val fragmentElements =
            roundEnv?.getElementsAnnotatedWith(FragmentDestination::class.java)
        val activityElements =
            roundEnv?.getElementsAnnotatedWith(ActivityDestination::class.java)

        if (fragmentElements?.isNotEmpty() == true || activityElements?.isNotEmpty() == true) {
            val destMap = hashMapOf<String, JSONObject>()
            fragmentElements?.takeIf { it.isNotEmpty() }?.let {
                handleDestination(it, FragmentDestination::class.java, destMap)
            }

            activityElements?.takeIf { it.isNotEmpty() }?.let {
                handleDestination(it, ActivityDestination::class.java, destMap)
            }

            //  /app/src/main/assets
            try {
                filer?.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME)?.let {
                    val resourcePath = it.toUri().path
                    val appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4)
                    val assetsPath = appPath + "src/main/assets/"
                    val file = File(assetsPath)
                    if (!file.exists()) {
                        file.mkdir()
                    }

                    val outPutFile = File(file, OUTPUT_FILE_NAME)
                    if (outPutFile.exists()) {
                        outPutFile.delete()
                    }
                    outPutFile.createNewFile()
                    outPutFile.outputStream().use { output ->
                        JSON.toJSONString(destMap).byteInputStream().use { input ->
                            input.copyTo(output)
                        }
                    }
                }
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
        }
        return true
    }

    private fun handleDestination(
        elements: MutableSet<out Element>,
        annotationClaz: Class<out Annotation>,
        destMap: HashMap<String, JSONObject>
    ) {
        elements.forEach {
            val typeElement = it as? TypeElement ?: return
            var pageUrl : String = ""
            val clazName = typeElement.qualifiedName.toString()
            val id = abs(clazName.hashCode())
            var needLogin: Boolean = false
            var asStarter: Boolean = false
            var isFragment : Boolean = false

            val annotation = typeElement.getAnnotation(annotationClaz)
            if (annotation is FragmentDestination) {
                pageUrl = annotation.pageUrl
                asStarter = annotation.asStarter
                needLogin = annotation.needLogin
                isFragment = true
            } else if (annotation is ActivityDestination) {
                pageUrl = annotation.pageUrl
                asStarter = annotation.asStarter
                needLogin = annotation.needLogin
                isFragment = false
            }

            if (destMap.containsKey(pageUrl)) {
                messager?.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的pageUrl : $pageUrl")
            } else {
                val jsonObject = JSONObject().apply {
                    put("id", id)
                    put("needLogin", needLogin)
                    put("asStarter", asStarter)
                    put("pageUrl", pageUrl)
                    put("clazName", clazName)
                    put("isFragment", isFragment)
                }
                destMap[pageUrl] = jsonObject
            }
        }
    }


}