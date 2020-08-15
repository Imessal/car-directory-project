package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.CarHandler.{Car, CarForm, CarFormData, CarSet, NumberBuilder}
import services.CarService

@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents,
                               carService: CarService) extends AbstractController(controllerComponents) with Logging {

  def index() = Action { implicit request: Request[AnyContent] =>
    val cars = carService.listAllCars()
    Ok(views.html.index(CarForm.form, cars))
  }

  def addCar() = Action { implicit request =>
    CarForm.form.bindFromRequest.fold(
      errorForm => Ok(views.html.index(errorForm, CarSet(List()))),
      data => {
        val newCar = Car(NumberBuilder.buildNumber(data.number), data.brand, data.color, data.model, data.year)
        val res = carService.addCar(newCar)
        Redirect(routes.HomeController.index())
      }
    )
  }

  def deleteCar(number:String) = Action { implicit request =>
    carService.deleteCar(number)
    Redirect(routes.HomeController.index())
  }
}
