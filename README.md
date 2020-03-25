# Nutrinfo
Nutrinfo is my final project for Harvard University's CS50: Introduction to Computer Science. It is a REST API that allows applications to retrieve nutritional information about food in the form of a concise JSON response. I built Nutrinfo using Java, Dropwizard and SQLite

This documentation will help you to understand and utilise the Nutrinfo API


## Usage
The API has 3 endpoints: /all, /{id} and /search


### /all
Responses to /all will be paginated by a default limit of 20 and a default offset of 0

- Fetch basic info about all foods
  ```
  GET /api/v1/food/all
  ```
  ```json
  [
    {
      "id": 1,
      "name": "Abiyuch, raw",
      "amount": 100,
      "unit": "g",
      "calories": 69,
      "macronutrients": null,
      "micronutrients": null
    },
    {
      "id": 2,
      "name": "Apricots, raw",
      "amount": 100,
      "unit": "g",
      "calories": 48,
      "macronutrients": null,
      "micronutrients": null
    },
    {
      "id": 3,
      "name": "Arrowhead, raw",
      "amount": 100,
      "unit": "g",
      "calories": 99,
      "macronutrients": null,
      "micronutrients": null
    }
  ]
  ```

- Fetch basic and macronutrient info about all foods  
  ```
  GET /api/v1/food/all/macronutrients
  ```
  ```json
  [
    {
      "id": 1,
      "name": "Abiyuch, raw",
      "amount": 100,
      "unit": "g",
      "calories": 69,
      "macronutrients": {
        "fat": {
          "totalFat": 0.014,
          "saturatedFat": 0.014,
          "polyunsaturatedFat": 0,
          "monounsaturatedFat": 0,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 13.85,
          "fiber": 8.55,
          "sugar": 5.3
        },
        "protein": 1.5,
        "unit": "g"
      },
      "micronutrients": null
    },
    {
      "id": 2,
      "name": "Apricots, raw",
      "amount": 100,
      "unit": "g",
      "calories": 48,
      "macronutrients": {
        "fat": {
          "totalFat": 0.274,
          "saturatedFat": 0.027,
          "polyunsaturatedFat": 0.077,
          "monounsaturatedFat": 0.17,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 11.24,
          "fiber": 9.24,
          "sugar": 2
        },
        "protein": 1.4,
        "unit": "g"
      },
      "micronutrients": null
    },
    {
      "id": 3,
      "name": "Arrowhead, raw",
      "amount": 100,
      "unit": "g",
      "calories": 99,
      "macronutrients": {
        "fat": {
          "totalFat": 0,
          "saturatedFat": 0,
          "polyunsaturatedFat": 0,
          "monounsaturatedFat": 0,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 0,
          "fiber": 0,
          "sugar": 0
        },
        "protein": 5.33,
        "unit": "g"
      },
      "micronutrients": null
    }
  ] 
  ```

- Fetch basic, macronutrient and micronutrient info about all foods
  ```
  GET /api/v1/food/all/macronutrients/micronutrients
  ```
  ```json
  [
    {
      "id": 1,
      "name": "Abiyuch, raw",
      "amount": 100,
      "unit": "g",
      "calories": 69,
      "macronutrients": {
        "fat": {
          "totalFat": 0.014,
          "saturatedFat": 0.014,
          "polyunsaturatedFat": 0,
          "monounsaturatedFat": 0,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 13.85,
          "fiber": 8.55,
          "sugar": 5.3
        },
        "protein": 1.5,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.005,
        "vitaminC": 54.1,
        "vitaminD": 0,
        "calcium": 8,
        "iron": 1.61,
        "potassium": 304,
        "sodium": 20,
        "unit": "mg"
      }
    },
    {
      "id": 2,
      "name": "Apricots, raw",
      "amount": 100,
      "unit": "g",
      "calories": 48,
      "macronutrients": {
        "fat": {
          "totalFat": 0.274,
          "saturatedFat": 0.027,
          "polyunsaturatedFat": 0.077,
          "monounsaturatedFat": 0.17,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 11.24,
          "fiber": 9.24,
          "sugar": 2
        },
        "protein": 1.4,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.096,
        "vitaminC": 10,
        "vitaminD": 0,
        "calcium": 13,
        "iron": 0.39,
        "potassium": 259,
        "sodium": 1,
        "unit": "mg"
      }
    },
    {
      "id": 3,
      "name": "Arrowhead, raw",
      "amount": 100,
      "unit": "g",
      "calories": 99,
      "macronutrients": {
        "fat": {
          "totalFat": 0,
          "saturatedFat": 0,
          "polyunsaturatedFat": 0,
          "monounsaturatedFat": 0,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 0,
          "fiber": 0,
          "sugar": 0
        },
        "protein": 5.33,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0,
        "vitaminC": 1.1,
        "vitaminD": 0,
        "calcium": 10,
        "iron": 2.57,
        "potassium": 922,
        "sodium": 22,
        "unit": "mg"
      }
    }
  ]
  ```


### /{id}
- Fetch basic info about the food by {id}
  ```
  GET /api/v1/food/{id}
  ```
  For example, when {id} is 13:
  ```json
  {
    "id": 13,
    "name": "Broccoli, raw",
    "amount": 100,
    "unit": "g",
    "calories": 34,
    "macronutrients": null,
    "micronutrients": null
  }
  ```

- Fetch basic and macronutrient info about the food by {id}
  ```
  GET /api/v1/food/{id}/macronutrients
  ```
  For example, when {id} is 13:
  ```json
  {
    "id": 13,
    "name": "Broccoli, raw",
    "amount": 100,
    "unit": "g",
    "calories": 34,
    "macronutrients": {
      "fat": {
        "totalFat": 0.257,
        "saturatedFat": 0.114,
        "polyunsaturatedFat": 0.112,
        "monounsaturatedFat": 0.031,
        "cholesterol": 0
      },
      "carbohydrate": {
        "totalCarbohydrate": 4.3,
        "fiber": 1.7,
        "sugar": 2.6
      },
      "protein": 2.82,
      "unit": "g"
    },
    "micronutrients": null
  }
  ```

- Fetch basic, macronutrient and micronutrient info about the food by {id}
  ```
  GET /api/v1/food/{id}/macronutrients/micronutrients
  ```
  For example, when {id} is 13:
  ```json
  {
    "id": 13,
    "name": "Broccoli, raw",
    "amount": 100,
    "unit": "g",
    "calories": 34,
    "macronutrients": {
      "fat": {
        "totalFat": 0.257,
        "saturatedFat": 0.114,
        "polyunsaturatedFat": 0.112,
        "monounsaturatedFat": 0.031,
        "cholesterol": 0
      },
      "carbohydrate": {
        "totalCarbohydrate": 4.3,
        "fiber": 1.7,
        "sugar": 2.6
      },
      "protein": 2.82,
      "unit": "g"
    },
    "micronutrients": {
      "vitaminA": 0.031,
      "vitaminC": 89.2,
      "vitaminD": 0,
      "calcium": 47,
      "iron": 0.73,
      "potassium": 316,
      "sodium": 33,
      "unit": "mg"
    }
  }
  ```


### /search
Responses to /search will be paginated by a default limit of 20 and a default offset of 0

- Return (basic, macronutrient and micronutrient info about) all foods where name is like {name}
  ```
  GET /api/v1/food/search/name/q={name}
  ```
  For example, when {name} is berries:
  ```json
  [
    {
      "id": 10,
      "name": "Blueberries, raw",
      "amount": 100,
      "unit": "g",
      "calories": 57,
      "macronutrients": {
        "fat": {
          "totalFat": 0.22099999,
          "saturatedFat": 0.028,
          "polyunsaturatedFat": 0.146,
          "monounsaturatedFat": 0.047,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 12.360001,
          "fiber": 9.96,
          "sugar": 2.4
        },
        "protein": 0.74,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.003,
        "vitaminC": 9.7,
        "vitaminD": 0,
        "calcium": 6,
        "iron": 0.28,
        "potassium": 77,
        "sodium": 1,
        "unit": "mg"
      }
    },
    {
      "id": 28,
      "name": "Cranberries, raw",
      "amount": 100,
      "unit": "g",
      "calories": 46,
      "macronutrients": {
        "fat": {
          "totalFat": 0.081,
          "saturatedFat": 0.008,
          "polyunsaturatedFat": 0.055,
          "monounsaturatedFat": 0.018,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 7.87,
          "fiber": 4.27,
          "sugar": 3.6
        },
        "protein": 0.46,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.003,
        "vitaminC": 14,
        "vitaminD": 0,
        "calcium": 8,
        "iron": 0.23,
        "potassium": 80,
        "sodium": 2,
        "unit": "mg"
      }
    },
    {
      "id": 31,
      "name": "Elderberries, raw",
      "amount": 100,
      "unit": "g",
      "calories": 73,
      "macronutrients": {
        "fat": {
          "totalFat": 0.34999996,
          "saturatedFat": 0.023,
          "polyunsaturatedFat": 0.247,
          "monounsaturatedFat": 0.08,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 7,
          "fiber": 0,
          "sugar": 7
        },
        "protein": 0.66,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.03,
        "vitaminC": 36,
        "vitaminD": 0,
        "calcium": 38,
        "iron": 1.6,
        "potassium": 280,
        "sodium": 6,
        "unit": "mg"
      }
    }
  ]
  ```

- Filter (basic, macronutrient and micronutrient info about) all foods by {macronutrient} descending
  ```
  GET /api/v1/food/search/macronutrient/q={macronutrient}
  ```
  For example, when {macronutrient} is fiber:
  ```json
  [
    {
      "id": 79,
      "name": "Tamarinds, raw",
      "amount": 100,
      "unit": "g",
      "calories": 239,
      "macronutrients": {
        "fat": {
          "totalFat": 0.512,
          "saturatedFat": 0.272,
          "polyunsaturatedFat": 0.059,
          "monounsaturatedFat": 0.181,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 43.899998,
          "fiber": 38.8,
          "sugar": 5.1
        },
        "protein": 2.8,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.002,
        "vitaminC": 3.5,
        "vitaminD": 0,
        "calcium": 74,
        "iron": 2.8,
        "potassium": 628,
        "sodium": 28,
        "unit": "mg"
      }
    },
    {
      "id": 1455,
      "name": "Rhubarb, frozen, cooked, with sugar",
      "amount": 100,
      "unit": "g",
      "calories": 116,
      "macronutrients": {
        "fat": {
          "totalFat": 0.049000002,
          "saturatedFat": 0.014,
          "polyunsaturatedFat": 0.025,
          "monounsaturatedFat": 0.01,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 30.7,
          "fiber": 28.7,
          "sugar": 2
        },
        "protein": 0.39,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.004,
        "vitaminC": 3.3,
        "vitaminD": 0,
        "calcium": 145,
        "iron": 0.21,
        "potassium": 96,
        "sodium": 1,
        "unit": "mg"
      }
    },
    {
      "id": 1730,
      "name": "Sweet potato, cooked, candied, home-prepared",
      "amount": 100,
      "unit": "g",
      "calories": 164,
      "macronutrients": {
        "fat": {
          "totalFat": 12.25,
          "saturatedFat": 2.171,
          "polyunsaturatedFat": 0.198,
          "monounsaturatedFat": 0.881,
          "cholesterol": 9
        },
        "carbohydrate": {
          "totalCarbohydrate": 29.35,
          "fiber": 27.25,
          "sugar": 2.1
        },
        "protein": 0.89,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.349,
        "vitaminC": 9,
        "vitaminD": 0.000100000005,
        "calcium": 26,
        "iron": 0.79,
        "potassium": 178,
        "sodium": 119,
        "unit": "mg"
      }
    }
  ]
  ```
  **Query parameters**
  * saturated_fat
  * polyunsaturated_fat
  * monounsaturated_fat
  * cholesterol
  * fiber
  * sugar
  * protein

- Filter (basic, macronutrient and micronutrient info about) all foods by {micronutrient} descending
  ```
  GET /api/v1/food/search/micronutrient/q={micronutrient}
  ```
  For example, when {micronutrient} is vitamin_c:
  ```json
  [
    {
      "id": 86,
      "name": "Acerola juice, raw",
      "amount": 100,
      "unit": "g",
      "calories": 23,
      "macronutrients": {
        "fat": {
          "totalFat": 0.24000001,
          "saturatedFat": 0.068,
          "polyunsaturatedFat": 0.09,
          "monounsaturatedFat": 0.082,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 4.8,
          "fiber": 4.5,
          "sugar": 0.3
        },
        "protein": 0.4,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.025,
        "vitaminC": 1600,
        "vitaminD": 0,
        "calcium": 10,
        "iron": 0.5,
        "potassium": 97,
        "sodium": 3,
        "unit": "mg"
      }
    },
    {
      "id": 499,
      "name": "Peppers, hot chili, green, raw",
      "amount": 100,
      "unit": "g",
      "calories": 40,
      "macronutrients": {
        "fat": {
          "totalFat": 0.141,
          "saturatedFat": 0.021,
          "polyunsaturatedFat": 0.109,
          "monounsaturatedFat": 0.011,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 6.6,
          "fiber": 5.1,
          "sugar": 1.5
        },
        "protein": 2,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.059,
        "vitaminC": 242.5,
        "vitaminD": 0,
        "calcium": 18,
        "iron": 1.2,
        "potassium": 340,
        "sodium": 7,
        "unit": "mg"
      }
    },
    {
      "id": 152,
      "name": "Guavas, common, raw",
      "amount": 100,
      "unit": "g",
      "calories": 68,
      "macronutrients": {
        "fat": {
          "totalFat": 0.76,
          "saturatedFat": 0.272,
          "polyunsaturatedFat": 0.401,
          "monounsaturatedFat": 0.087,
          "cholesterol": 0
        },
        "carbohydrate": {
          "totalCarbohydrate": 14.32,
          "fiber": 8.92,
          "sugar": 5.4
        },
        "protein": 2.55,
        "unit": "g"
      },
      "micronutrients": {
        "vitaminA": 0.031,
        "vitaminC": 228.3,
        "vitaminD": 0,
        "calcium": 18,
        "iron": 0.26,
        "potassium": 417,
        "sodium": 2,
        "unit": "mg"
      }
    }
  ]
  ```
  **Query parameters**
  *  vitamin_a
  *  vitamin_c
  *  vitamin_d
  *  calcium
  *  iron
  *  potassium
  *  sodium


If something in the API is confusing to you, you can [email](mailto:kristina_head@icloud.com) me
