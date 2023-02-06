import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import {HttpErrorResponse} from '@angular/common/http';
import { Component } from '@angular/core';
import {BoardComponent} from "./board/board.component";

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private url ='http://localhost:8080';
  constructor(private http: HttpClient) {}
  addMachine(id:string){
    this.http.get(this.url+'/'+"addM?id="+id,).subscribe();
  }
  addQ(id:string){
    this.http.get(this.url+'/'+"addQ?id="+id).subscribe();
  }
  connect(machine:string,queue:string,fromQtoM:boolean){
    this.http.get(this.url+'/'+"connect?machineId="+machine+"&queueId="+queue+"&fromQtoM="+fromQtoM).subscribe();
  }
  clear(){
    this.http.get(this.url+"/clear").subscribe();
  }
  run(pro:number){
    BoardComponent.queue["Q1"]=pro;
    console.log( BoardComponent.texts["Q1"]);
    BoardComponent.texts["Q1"].text("Q1"+'\n'+BoardComponent.queue["Q1"],);
    this.http.get(this.url+"/run?pro="+pro).subscribe();
  }
  replay(){
    BoardComponent.texts["Q1"].text("Q1"+'\n'+BoardComponent.queue["Q1"],);
    this.http.get(this.url+"/replay").subscribe();
  }
  addProduct(product:number){
    this.http.get(this.url+"/product?pro="+product).subscribe();
  }
  askForChange(){
    this.http.get(this.url+"/change",{responseType:"text"}).subscribe((result:any)=>{
      if(result!="none"){
        let str=JSON.parse(result);
        if(str.from[0]=="Q"){
          BoardComponent.targets[str.to].fill(str.color);
          BoardComponent.queue[str.from]-=1;
          BoardComponent.texts[str.from].text(str.from+'\n'+BoardComponent.queue[str.from],);
        }else{
          BoardComponent.queue[str.to]+=1;
          BoardComponent.targets[str.from].fill('#00ff81');
          BoardComponent.texts[str.to].text(str.to+'\n'+BoardComponent.queue[str.to],);

        }
      }
    })
  }
}

