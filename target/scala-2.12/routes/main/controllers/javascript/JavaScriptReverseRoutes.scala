
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Sep 20 14:05:10 GMT-03:00 2017

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:28
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:23
  class ReverseCountController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:23
    def count: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.CountController.count",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "count"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseMusicas(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:18
    def valorMoedas: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Musicas.valorMoedas",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "musicas/valormoedas/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:9
    def meta: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Musicas.meta",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "musicas/meta/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:6
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Musicas.get",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "musicas/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:15
    def tracks: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Musicas.tracks",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "musicas/tracks/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Long]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:12
    def checkForUpdates: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Musicas.checkForUpdates",
      """
        function(versaoAtualizacaoBancoCliente0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "musicas/checkforupdates/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Int]].javascriptUnbind + """)("versaoAtualizacaoBancoCliente", versaoAtualizacaoBancoCliente0))})
        }
      """
    )
  
  }

  // @LINE:21
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:21
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:25
  class ReverseAsyncController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:25
    def message: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.AsyncController.message",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "message"})
        }
      """
    )
  
  }


}
