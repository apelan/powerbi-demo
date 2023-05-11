# PowerBI Demo

This repository contains a demo application which generates an embed token for Power BI reports using the [App owns data](https://learn.microsoft.com/en-us/javascript/api/overview/powerbi/embedding-solutions#embed-for-your-customers) with certificate-based authentication. This demo can generate tokens for single or multiple reports.

The demo involves extracting credentials from a .pfx certificate.

## Prerequisites

Before using this demo application, you need to have the following:

- Java 17
- Maven
- Certificate (.pfx) which contains credentials for PowerBI
- Power BI report, target workspace, and dataset ID to test
- Azure application

## Setup

Follow these steps to configure the demo application:

1. Change the `application.yml` file. 
```yaml
integration:
  power-bi:
    client-id: ${ADD_YOUR_CLIENT_ID}
    tenant-id: ${ADD_YOUR_TENANT_ID}
    certificate-path: "cert/${ADD_YOUR_CERTIFICATE_NAME}.pfx"
```
2. Add your certificate in the `resources/cert` package. Note that this is not a good practice since the certificate is added in the project codebase for testing purposes. Consider storing and fetching the certificate from Azure Key Vault.
3. In the `DemoController.java` file, add your dataset, report, and workspace IDs.

## Usage

To use the demo application, follow these steps:

1. Build the Maven project by running `mvn clean install`.
2. Run the application and access `http://localhost:8080/token`.
3. If everything is configured properly, you should see the token being generated:
```json
{
    "token": "jwt-token-here"
    "expiration": "2023-01-01T00:00:00Z"
}
```

## Useful Links

Here are some useful links related to Power BI and Azure:

- [Power BI Overview](https://learn.microsoft.com/en-us/javascript/api/overview/powerbi/)
- [Understanding the Different Embedding Solutions](https://learn.microsoft.com/en-us/javascript/api/overview/powerbi/embedding-solutions)
- [Embed Token](https://learn.microsoft.com/en-us/rest/api/power-bi/embed-token)
- [Service Principal Certificate](https://learn.microsoft.com/en-us/power-bi/developer/embedded/embed-service-principal-certificate)
- [Azure Key Vault](https://azure.microsoft.com/en-us/products/key-vault/)
