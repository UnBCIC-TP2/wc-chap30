## Futures

```
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


def longRunningAlgorithm() =
  Thread.sleep(10_000)
  42
  
val eventualInt = Future(longRunningAlgorithm())  
``` 
