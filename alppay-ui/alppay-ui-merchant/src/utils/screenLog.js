/* eslint-disable */
export const printANSI = () => {
  let text = `
    ___    __                       
   /   |  / /___  ____  ____ ___  __
  / /| | / / __ \\/ __ \\/ __ \`/ / / /
 / ___ |/ / /_/ / /_/ / /_/ / /_/ / 
/_/  |_/_/ .___/ .___/\__,_/\__, /  
        /_/   /_/          /____/   
 :: alppay ::        (v1.0.0.RELEASE)
 让支付接入更简单 : https://www.aialphay.com
`

  console.log(`%c${text}`, 'color: #fc4d50')

  console.log('%cThanks for using alppay!', 'color: #fff; font-size: 14px; font-weight: 300; text-shadow:#000 1px 0 0,#000 0 1px 0,#000 -1px 0 0,#000 0 -1px 0;')

}
