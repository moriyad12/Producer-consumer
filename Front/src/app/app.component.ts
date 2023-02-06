import {Component, OnInit} from '@angular/core';
import Konva from 'konva';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Producer-Consumer';

  constructor() {
  }
  str:string='';
  ngOnInit():void{

  }
}
