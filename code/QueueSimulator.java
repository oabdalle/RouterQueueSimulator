import java.lang.*;

public class QueueSimulator
{
  public enum Event { ARRIVAL, DEPARTURE };
  private double currTime;
  private double arrivalRate;
  private double serviceTime;
  private double timeForNextArrival;
  private double timeForNextDeparture;
  private double totalSimTime;
  LinkedListQueue<Data> buffer = new LinkedListQueue<Data>();
  LinkedListQueue<Data> eventQueue = new LinkedListQueue<Data>();
  private Event e;
  
  public double getRandTime(double arrivalRate)
  {
    double num, time1, max=1, min=0, randNUM;
    randNUM= Math.random();
    time1= (-1/arrivalRate) * (Math.log(1-randNUM));
    return time1;
  }
  
  public QueueSimulator(double aR, double servT, double simT)
  {
    arrivalRate = aR;
    serviceTime = servT;
    totalSimTime = simT;
    currTime = 0;
    timeForNextArrival = getRandTime(aR);
    timeForNextDeparture = timeForNextArrival + servT;
  }
  
  public double calcAverageWaitingTime()
  {
    int n = 0;
    double result = 0;
    while(!eventQueue.isEmpty())
    {
      Data temp = new Data();
      temp = eventQueue.dequeue();
      result += (temp.getDepartureTime() - temp.getArrivalTime());
      n++;
    }
    return (double)(result / n);
  }
  
  public double runSimulation()
  {
    int n = 0;
    timeForNextDeparture = totalSimTime + 1;
    while(currTime <= totalSimTime)
    {
      if(timeForNextDeparture < timeForNextArrival && !buffer.isEmpty())
      {
        e = Event.DEPARTURE;
        currTime = timeForNextDeparture;
        n--;
        if(n > 0)
          timeForNextDeparture = currTime + serviceTime;
        else
          timeForNextDeparture = totalSimTime + 1;
        Data d = new Data();
        d = buffer.dequeue();
        d.setDepartureTime(currTime);
        eventQueue.enqueue(d);
      }
      else
      {
        e = Event.ARRIVAL;
        currTime = timeForNextArrival;
        n++;
        timeForNextArrival = currTime + getRandTime(arrivalRate);
        if(n == 1)
          timeForNextDeparture = currTime + serviceTime;
        Data arrBuff = new Data();
        arrBuff.setArrivalTime(currTime);
        buffer.enqueue(arrBuff);
      }
    }
    return calcAverageWaitingTime();
  }

}






