package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.CarHandler.{Car, CarForm, CarFormData, NumberBuilder}
import services.CarService

import scala.concurrent.Future

@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents,
                               carService: CarService) extends AbstractController(controllerComponents) with Logging {

  def index() = Action.async { implicit request: Request[AnyContent] =>
    val cars = carService.listAllCars()
    Ok(views.html.index(CarForm.form, cars))
  }

  def addCar() = Action.async { implicit request =>
    CarForm.form.bindFromRequest.fold(
      errorForm => {
        logger.warn(s"Form submission with error: ${errorForm.errors}")
        Future.successful(Ok(views.html.index(errorForm, Seq.empty[Car])))
      },
      data => {
        val newCar = Car(data.number, data.brand, data.color, data.model, data.year)
        val res = carService.addCar(newCar)
        Redirect(routes.HomeController.index())
      }
    )
  }

  def deleteCar(number: Number): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    carService.deleteCar(number) map(res => Redirect(routes.HomeController.index())
  }
}
