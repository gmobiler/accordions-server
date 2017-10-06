
// @GENERATOR:play-routes-compiler
// @SOURCE:G:/Projetos/PlayFramework/accordions-server/conf/routes
// @DATE:Wed Oct 04 18:37:11 GMT-03:00 2017

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAuth Auth = new controllers.ReverseAuth(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseServicesController ServicesController = new controllers.ReverseServicesController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseCountController CountController = new controllers.ReverseCountController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseMusicas Musicas = new controllers.ReverseMusicas(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseHomeController HomeController = new controllers.ReverseHomeController(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAsyncController AsyncController = new controllers.ReverseAsyncController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAuth Auth = new controllers.javascript.ReverseAuth(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseServicesController ServicesController = new controllers.javascript.ReverseServicesController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseCountController CountController = new controllers.javascript.ReverseCountController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseMusicas Musicas = new controllers.javascript.ReverseMusicas(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseHomeController HomeController = new controllers.javascript.ReverseHomeController(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAsyncController AsyncController = new controllers.javascript.ReverseAsyncController(RoutesPrefix.byNamePrefix());
  }

}
