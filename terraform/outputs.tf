output "ecs_cluster_name" {
  value = aws_ecs_cluster.this.name
}

output "ecs_service_name" {
  value = aws_ecs_service.this.name
}

output "api_gateway_url" {
  value = aws_apigatewayv2_api.this.api_endpoint
}

output "alb_dns_name" {
  value = aws_lb.this.dns_name
}
