import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Environment {

  private config: any;

  async loadConfig() {
    return fetch('/assets/environment.json')
      .then(res => res.json())
      .then(json => {
        this.config = json;
        console.log("Config read from environment.json: " + JSON.stringify(this.config));
      });
  }

  get(key: string) {
    const keys = key.split(".");
    let value = this.config;

    for (const k of keys) {
      value = value?.[k];
      if (value === undefined) break;
    }

    console.log(`${key}: ${value}`);
    return value;
  }

}
