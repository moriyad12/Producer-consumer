import { Component, OnInit } from '@angular/core';
import Konva from "konva";
import {ServiceService} from "../service.service";
// import {WebSocketAPI} from "../WebSocketAPI";

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  static layer:any;
  static stage:any;
  static tr:any;
  static start:boolean=false;
  static end:boolean=false;
  static connections:any=[];
  static lines:any=[];
  static machIndex:number=1;
  static queueIndex:number=1;
  static targets:any=new Map<string,any>();
  static texts:any=new Map<string,any>();
  static queue:any=new Map<string,number>();
  products:number=10;

  constructor(private service:ServiceService) { }
  ngOnInit(): void {
    this.service.clear();
    BoardComponent.layer=new Konva.Layer();
    BoardComponent.tr =new Konva.Transformer();
    BoardComponent.stage = new Konva.Stage({
      container: 'container',
      width: window.innerWidth,
      height: window.innerHeight,
    });
    BoardComponent.stage.on('click tap', function (e: { target: any; }) {
      if (e.target === BoardComponent.stage) {
        BoardComponent.tr.nodes([]);
      }
    });
  }

  circle(){
    var circle = new Konva.Circle({
      x: BoardComponent.stage.width() / 2,
      y: BoardComponent.stage.height() / 2,
      radius: 50,
      fill: '#09f1f1',
      stroke: 'black',
      strokeWidth: 1,
      draggable:true,
      id:BoardComponent.makeQid(),
      name:"0",
    });
    this.service.addQ(circle.id());
    BoardComponent.queue[circle.id()]=0;
    var text=new Konva.Text({
      x:circle.x()-20,
      y:circle.y()-10,
      text:circle.id()+'\n'+BoardComponent.queue[circle.id()],
      fill:"black",
      fontSize:30,
    });
    console.log(text);
    BoardComponent.texts[circle.id()]=text;
    console.log(BoardComponent.texts[circle.id()],circle.id());
    circle.on("dblclick", function (){
      console.log(BoardComponent.start,BoardComponent.end);
      if(BoardComponent.start==false){
        BoardComponent.connections[0]=circle;
        BoardComponent.start=true;
        console.log("hi"+BoardComponent.connections);
      }else if(BoardComponent.end==false){
        BoardComponent.connections[1]=circle;

        BoardComponent.end=true;
        console.log("hi"+BoardComponent.connections);
      }
    })

    circle.on("dragmove",function (){

      text.x(circle.x()-20);
      text.y(circle.y()-10);
      BoardComponent.updateObjects();

    })
    BoardComponent.targets[circle.id()]=circle;
    BoardComponent.layer.add(circle);
    BoardComponent.layer.add(text);

    BoardComponent.stage.add(BoardComponent.layer);
  }
  rect(){
    var box = new Konva.Rect({
      x: BoardComponent.stage.width() / 2,
      y: BoardComponent.stage.height() / 2,
      width: 100,
      height: 100,
      fill: '#00ff81',
      stroke: 'black',
      strokeWidth: 1,
      id:BoardComponent.makeMid(),
      name:"0",
      draggable:true,
    });
    this.service.addMachine(box.id());

    var text=new Konva.Text({
      x:box.x()+25,
      y:box.y()+25,
      text:box.id(),
      fill:"black",
      fontSize:30,
      align:"center",
    });

    box.on("dblclick", function (){
      if(BoardComponent.start==false){
        BoardComponent.connections[0]=box;
        console.log("hi"+BoardComponent.connections);

        BoardComponent.start=true;
      }else if(BoardComponent.end==false){
        BoardComponent.connections[1]=box;
        BoardComponent.end=true;
        console.log("hi"+BoardComponent.connections);
      }
    })

    box.on("dragmove",function (){
      text.x(box.x()+25);
      text.y(box.y()+25);      BoardComponent.updateObjects();

    })
    BoardComponent.targets[box.id()]=box;
    BoardComponent.layer.add(box);
    BoardComponent.layer.add(text);
    BoardComponent.stage.add(BoardComponent.layer);
  }
  line(){
    console.log("hi"+BoardComponent.connections);
    if(BoardComponent.start==false||BoardComponent.end==false){
      alert("You need to choose elements first"      );
      return;
    }
    BoardComponent.start=false;
    BoardComponent.end=false;
    if(BoardComponent.connections[0].id()[0]=='Q'){
      this.service.connect(BoardComponent.connections[1].id(),BoardComponent.connections[0].id(),true);
    }else{
      this.service.connect(BoardComponent.connections[0].id(),BoardComponent.connections[1].id(),false);
    }
    var points=BoardComponent.getConnectorPoints(BoardComponent.connections[0],BoardComponent.connections[1]);
    console.log(points);

    let arrow = new Konva.Arrow({

      points: points,
      pointerLength: 20,
      pointerWidth: 20,
      fill: 'black',
      stroke: 'black',
      strokeWidth: 1,
    });
    BoardComponent.lines.push({
      arrow: arrow,
      from: BoardComponent.connections[0],
      to: BoardComponent.connections[1],
    });
    BoardComponent.layer.add(arrow);
    BoardComponent.stage.add(BoardComponent.layer);
    BoardComponent.connections=[];
  }

  static  makeMid() {
    return "M"+BoardComponent.machIndex++;
  }
  static  makeQid() {
    return "Q"+BoardComponent.queueIndex++;
  }
  static getConnectorPoints(from:any, to:any) {
    const dx = to.x() - from.x();
    const dy = to.y() - from.y();

    let angle = Math.atan2(-dy, dx);
    const radius = 50;
    console.log('hi');
    if(from.id()[0]=='M') {
      var array = [from.x() + -radius * Math.cos(angle + Math.PI) + 50, from.y() + radius * Math.sin(angle + Math.PI) + 50, to.x() + -radius * Math.cos(angle), to.y() + radius * Math.sin(angle)];
      return array;
    }
    var array = [from.x() + -radius * Math.cos(angle + Math.PI) , from.y() + radius * Math.sin(angle + Math.PI) , to.x() + -radius * Math.cos(angle)+50, to.y() + radius * Math.sin(angle)+50];
    return array;
  }
  static updateObjects() {
    console.log(this.lines);
    BoardComponent.lines.forEach((connect: any) =>{

      console.log("HI");
      const points = BoardComponent.getConnectorPoints(connect.from, connect.to);
      connect.arrow.points(points);
    });
  }
  clear(){
    this.service.clear();
    BoardComponent.layer=new Konva.Layer();
    BoardComponent.tr =new Konva.Transformer();
    BoardComponent.stage = new Konva.Stage({
      container: 'container',
      width: window.innerWidth,
      height: window.innerHeight,
    });
     BoardComponent.start=false;
    BoardComponent.end=false;
    BoardComponent.connections=[];
    BoardComponent.lines=[];
    BoardComponent.machIndex=1;
    BoardComponent.queueIndex=1;
    BoardComponent.targets=new Map<string,any>();
    BoardComponent.texts=new Map<string,any>();
    BoardComponent.queue=new Map<string,number>();
    this.products=10;
  }

  run(){
    this.service.run(this.products);

    let var2=this.service;
    var v=setInterval(function (vv:any) {
      var2.askForChange();

    });
  }

  replay(){
    BoardComponent.queue["Q1"]=this.products;
    let st=2;
    while (st<BoardComponent.queueIndex){
      BoardComponent.queue["Q"+st]=0;
      BoardComponent.texts["Q"+st].text("Q"+st+'\n'+BoardComponent.queue["Q"+st],);
      st++;
    }
    this.service.replay();
    let var2=this.service;
    var v=setInterval(function (vv:any) {
      var2.askForChange();
    });
  }
}
