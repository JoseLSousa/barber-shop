import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'servicePrice'
})
export class ServicePricePipe implements PipeTransform {

  transform(value: string, value2: number): unknown {
    const res = `${value} - R$ ${value2 / 10}`
    return res;
  }

}
