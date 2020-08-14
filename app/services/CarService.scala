package services
import models.CarHandler._

class  CarService {
  val cars = CarSet(List())

  def addCar(car: Car): CarSet = {
    cars.add(car)
  }

  def deleteCar(number: String): CarSet = {
    cars.delete(number)
  }

  def listAllCars(): CarSet = {
    cars.listAll()
  }
}
