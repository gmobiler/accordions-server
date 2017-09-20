
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Sep 20 14:05:10 GMT-03:00 2017

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:28
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:28
    def versioned(file:Asset): Call = {
      implicit val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:23
  class ReverseCountController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:23
    def count(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "count")
    }
  
  }

  // @LINE:6
  class ReverseMusicas(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:18
    def valorMoedas(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/valormoedas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:9
    def meta(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/meta/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:6
    def get(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:15
    def tracks(id:Long): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/tracks/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)))
    }
  
    // @LINE:12
    def checkForUpdates(versaoAtualizacaoBancoCliente:Int): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "musicas/checkforupdates/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("versaoAtualizacaoBancoCliente", versaoAtualizacaoBancoCliente)))
    }
  
  }

  // @LINE:21
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:21
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:25
  class ReverseAsyncController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:25
    def message(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "message")
    }
  
  }


}
