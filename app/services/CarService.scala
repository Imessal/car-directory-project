package services
import com.google.inject.Inject
import models.CarHandler._

import scala.concurrent.Future

class  CarService @Inject() (cars: Cars) {
  def addCar(car: Car): Future[String] = {
    cars.add(car)
  }

  def deleteCar(number: String): Future[Int] = {
    cars.delete(number)
  }

  def findCar(number: String): Future[Option[Car]] = {
    cars.get(number)
  }

  def listAllCars(): Future[Seq[Car]] = {
    cars.listAll
  }
}
