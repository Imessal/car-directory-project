package controllers

import javax.inject._
import java.util.Calendar
import play.api._
import play.api.mvc._
import models.CarHandler.{Car, CarForm, CarFormData, NumberBuilder}
import services.CarService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents,
                               carService: CarService) extends AbstractController(controllerComponents) with Logging {

  def index(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    carService.listAllCars() map { cars =>
      Ok(views.html.index(CarForm.form, cars))
    }
  }

  def register(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    carService.listAllCars() map { cars =>
      Ok(views.html.register(CarForm.form))
    }
  }

  def addCar(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    CarForm.form.bindFromRequest.fold(
      errorForm => {
        logger.warn(s"Error: ${errorForm.errors}")
        Future.successful(Ok(views.html.index(errorForm, Seq.empty[Car])))
      },
      data => {
        if (data.toString == "bad number") {
          carService.listAllCars() map { res =>
            Ok(views.html.error(Seq.empty[Car]))
          }
        }
        val newCar = Car(0, data.number, data.brand, data.model, data.color,
          data.horse_forces, data.owners_count, data.year, Timer.now())

        carService.addCar(newCar) map { res =>
          Redirect(routes.HomeController.index())}
      }
    )
  }

  def deleteCar(number: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    carService.deleteCar(number) map { res =>
      Redirect(routes.HomeController.index())
    }
  }
}

object Timer {
  def now(): String = {
    val c = Calendar.getInstance()
    val day = c.get(Calendar.YEAR).toString + "-" +
      c.get(Calendar.MONTH).toString + "-" +
      c.get(Calendar.DAY_OF_MONTH).toString

    val time = c.get(Calendar.HOUR_OF_DAY).toString + ":" +
      c.get(Calendar.MINUTE).toString + ":" +
      c.get(Calendar.SECOND).toString

    day + " " + time
  }
}